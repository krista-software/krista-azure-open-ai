/*
 * Azure Open Ai Extension for Krista
 * Copyright (C) 2025 Krista Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>. 
 */

package app.krista.extensions.impl;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import app.krista.extension.util.InvokerAttributeProvider;
import app.krista.extensions.util.OpenAIConstants;
import app.krista.extensions.util.RateLimitSupport;
import app.krista.extensions.util.StringI18N;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QueryImpl {

    private static final Logger log = LoggerFactory.getLogger(QueryImpl.class);
    @Inject
    @Named(OpenAIConstants.API_KEY)
    private InvokerAttributeProvider<String> apiKey;
    @Inject
    @Named(OpenAIConstants.MODEL)
    private InvokerAttributeProvider<String> modelName;

    @Inject
    @Named(OpenAIConstants.SERVICE_URL)
    private InvokerAttributeProvider<String> serviceUrl;

    static private final int MAX_CALLS_MIN = 15;    // actual is 30
    static private final int MAX_TOKENS_MIN = 10000;
    static private final RateLimitSupport limiter = new RateLimitSupport(MAX_CALLS_MIN, Duration.ofMinutes(1),
            (int) (MAX_TOKENS_MIN * .8), Duration.ofMinutes(1), Duration.ofMinutes(1));

    private static OkHttpClient client = null;

    static private final double PRECISE_TEMP = .3;
    static private final double CREATIVE_TEMP = .8;
    static private final double PRECISE_TOPP = .2;
    static private final double CREATIVE_TOPP = .8;
    static private final int balancedInputOutTokens = 4*1024;
    static private final int maxResponseTokens = 512;

    private String getModelName(String modelDisplayName) {
        if (modelDisplayName.equals(OpenAIConstants.GPT_4)) {
            return "gpt-4";
        }
        return "gpt-3.5-turbo-16k";
    }

    public String queryModel(String prompt) {
        try {
            log.debug("Processing AI query request with prompt length: " + prompt.length() + " characters");
            if(apiKey.get() == null || apiKey.get().isBlank()){
                throw new IllegalArgumentException("Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource.");
            }
            if(modelName.get() == null || modelName.get().isBlank()){
                throw new IllegalArgumentException("AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration.");
            }
            ChatGPTResponse response = execute(prompt, apiKey.get(), modelName.get(), serviceUrl.get());
            if (response == null) {
                log.warn("Received null response from Azure OpenAI service for request");
                return "We encountered an unexpected issue while processing your request. This may be due to a temporary service interruption. Please try your request again. If the problem persists, please contact support at support@krista.ai with details about your query.";
            } else {
                log.debug("Successfully received response from Azure OpenAI service");
                return response.getAnswer();
            }
        } catch (IOException ioe) {
            return "The Azure OpenAI service is currently experiencing high demand or connectivity issues. Please wait a moment and try your request again. If this continues, check the Azure service status or contact your administrator.";
        }
    }

    synchronized static private void confirmHttpClient() {

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);
            client = builder.build();
        }
    }

    public ChatGPTResponse execute(String rawDocumentJson, String apiKey, String modelName, String serviceUrl) throws IOException {

        rawDocumentJson = addModelParameters(rawDocumentJson, modelName);

        confirmHttpClient();

        IOException caughtError = null;
        for (int i = 1; i < 4; i++) {
            log.debug("rawDocumentJson: " + rawDocumentJson);

            synchronized (limiter) {
                limiter.waitUntilReady(rawDocumentJson.length() / 3);
            }

            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, rawDocumentJson);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(serviceUrl)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("api-key", apiKey)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Accept", "application/json; charset=utf-8")
                    .post(body)
                    .build();

            try {
                okhttp3.Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                String responseJson = responseBody == null ? null : responseBody.string();
                response.close();

                if (response.code() == 429) {

                    briefPause();
                    throw new IOException("Rate limit exceeded, we'll slow down right here and try again.");
                }

                if (response.code() != 200) {
                    log.error("Azure OpenAI service returned error code: " + response.code());
                    if (StringI18N.isEmpty(responseJson)) {
                        log.error("No response body received from Azure OpenAI service");
                    } else {
                        log.error("Azure OpenAI error response: " + responseJson);
                    }
                    JsonObject chat = new Gson().fromJson(responseJson, JsonObject.class);
                    String errorMessage = chat.get("error").getAsJsonObject().get("message").toString();
                    throw new IllegalArgumentException(errorMessage);
                }

                if (StringI18N.isEmpty(responseJson)) {
                    log.error("Received empty response body from Azure OpenAI service");
                    return null;
                }
                ChatGPTResponse chat = new Gson().fromJson(responseJson, ChatGPTResponse.class);
                if (chat.getChoices() == null || chat.getChoices().isEmpty()) {
                    log.warn("Azure OpenAI service returned no response choices");
                    return null;
                }
                if (chat.getChoices().size() > 1) {
                    log.debug("Azure OpenAI returned multiple choices, using the first one");
                }
                return chat;

            } catch (IOException e) {

                caughtError = e;
                log.error("Azure OpenAI request failed, attempt " + i + " of 3: " + e.getMessage(), e);
                briefPause();
            }
        }

        throw caughtError;

    }

    private String addModelParameters(String rawDocumentJson, String modelname) {
        try{
            JsonObject json = new Gson().fromJson(rawDocumentJson, JsonObject.class);
            json.addProperty("model", getModelName(modelname));
            int tokensUsed = rawDocumentJson.length() / 3;
            int responseTokens;
            if (tokensUsed < balancedInputOutTokens)
                responseTokens = balancedInputOutTokens;
            else
                responseTokens = maxResponseTokens;

            json.addProperty("temperature", PRECISE_TEMP);
            json.addProperty("top_p", PRECISE_TOPP);
            json.addProperty("max_tokens", responseTokens);
            return json.toString();
        }catch (JsonSyntaxException cause){
            throw new IllegalArgumentException("The prompt must be in valid JSON format with a 'messages' array. Example: {\"messages\": [{\"role\": \"user\", \"content\": \"Your question here\"}]}. Please check your JSON syntax and try again.");
        }
    }



    public static void briefPause() {
        try {
            Thread.sleep(250L);
        } catch (InterruptedException ignore) {
        }
    }

    public String getAIModel() {
        return "MS_Azure_OpenAI_4";
    }

}
