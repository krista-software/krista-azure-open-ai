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

package app.krista.extensions;

import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

import app.krista.extension.executor.Invoker;
import app.krista.extension.impl.anno.*;
import app.krista.extensions.impl.QueryImpl;
import app.krista.extensions.service.RegistryService;
import app.krista.extensions.util.OpenAIConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Java(version = Java.Version.JAVA_21)
@Extension(version = "1.0.1", name = "MS Azure Open AI", jaxrsId = "azure_openai")
@Field.Text(value = OpenAIConstants.API_KEY, isSecured = true)
@Field.PickOne(value = OpenAIConstants.MODEL, values = {OpenAIConstants.GPT_35, OpenAIConstants.GPT_4})
@Field.Text(value = OpenAIConstants.SERVICE_URL)
@StaticResource(path = "docs", file = "docs")
public class AzureOpenAIExtension {

    private static final Logger log = LoggerFactory.getLogger(AzureOpenAIExtension.class);

    private final QueryImpl query;
    private final AzureOpenAIAttributes azureOpenAIAttributes;
    private final RegistryService registryService;
    private final Invoker invoker;

    @Inject
    public AzureOpenAIExtension(@Named("self") Invoker invoker, QueryImpl query, AzureOpenAIAttributes azureOpenAIAttributes, RegistryService registryService) {
        this.query = query;
        this.azureOpenAIAttributes = azureOpenAIAttributes;
        this.registryService = registryService;
        this.invoker = invoker;
    }

    @InvokerRequest(InvokerRequest.Type.VALIDATE_ATTRIBUTES)
    public void validateAttributes(Map<String, Object> attributes) {

        if (!(attributes.get(OpenAIConstants.API_KEY) instanceof String)) {
            throw new IllegalArgumentException("Azure OpenAI API Key is required. Please provide a valid API key from your Azure OpenAI resource. You can find this in the Azure portal under 'Keys and Endpoint'.");

        }

        if (!(attributes.get(OpenAIConstants.MODEL) instanceof String)) {
            throw new IllegalArgumentException("Model selection is required. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' from the dropdown menu. Ensure the selected model is deployed in your Azure OpenAI resource.");

        }

        this.testConnection((String)attributes.get(OpenAIConstants.API_KEY), (String)attributes.get(OpenAIConstants.MODEL), (String) attributes.get(OpenAIConstants.SERVICE_URL));

    }

    @InvokerRequest(InvokerRequest.Type.TEST_CONNECTION)
    public void testConnection() {
        this.testConnection(this.azureOpenAIAttributes.getApiKey(), this.azureOpenAIAttributes.getModelName(), this.azureOpenAIAttributes.getServiceUrl());
    }

    private void testConnection(String apiKey, String modelName, String serviceUrl) {
        JsonObject json = new JsonObject();
        json.addProperty("role", "user");
        json.addProperty("content", "Hello there!");
        JsonArray messages = new JsonArray();
        messages.add(json);
        JsonObject finalJson = new JsonObject();
        finalJson.add("messages", messages);
        String jsonString = finalJson.toString();
        try {
            query.execute(jsonString, apiKey, modelName, serviceUrl);
        } catch (IOException e) {
            log.info("Connection test failed: " + e.getMessage());
            throw new IllegalArgumentException("Unable to connect to Azure OpenAI service. Please verify: (1) Your API key is correct and active, (2) Your Service URL is properly formatted (e.g., https://your-resource.openai.azure.com/), (3) The selected model is deployed in your Azure resource, (4) Your network allows HTTPS connections to Azure. Error details: " + e.getMessage());
        }

    }

    @InvokerRequest(InvokerRequest.Type.CUSTOM_TABS)
    public Map<String, String> customTabs() {
        return Map.of("Documentation", "static/docs");
    }

    @InvokerRequest(InvokerRequest.Type.INVOKER_UPDATED)
    public void onInvokerUpdated(Map<String, Object> oldAttributes, Map<String, Object> newAttributes) {
        log.info("Updating attributes...");
        registryService.informConnections(newAttributes, invoker.getInvokerId());
    }

}

