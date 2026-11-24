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

package app.krista.extensions.service;

import app.krista.extensions.model.ServiceConfig;
import app.krista.extensions.model.ServiceMap;
import app.krista.extensions.model.ServiceType;
import app.krista.extensions.store.ServiceStore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class RegistryService {
    private final ServiceStore serviceStore;
    private final Gson gson = new Gson();
    private final OkHttpClient httpClient = new OkHttpClient();
    private final String LOCAL_HOST_NAME = "extension.local.eng.krista.app";
    private final String RESOLVED_LOCAL_HOST_NAME = "localhost:8765";
    //private final CapabilitiesService capabilitiesService;

    @Inject
    public RegistryService(ServiceStore serviceStore) {
        this.serviceStore = serviceStore;
        //this.capabilitiesService = capabilitiesService;
    }
    public void informConnections(Map<String, Object> newAttributes, String invokerId) {
        ServiceMap serviceMap = serviceStore.get();
        newAttributes.put("Invoker ID", invokerId);
        for (ServiceConfig serviceConfig : serviceMap.getConnections()) {
            if (serviceConfig.getModelType().equals(ServiceType.DEFAULT)) {
                executeHttpCallToConnection("llm", serviceConfig.getCallbackUrl(), gson.toJson(newAttributes));
            } else {
                executeHttpCallToConnection("fallback", serviceConfig.getCallbackUrl(),  gson.toJson(newAttributes));
            }
        }
    }

    private JsonObject executeHttpCallToConnection(String endPoint, String callbackUrl, String payload) {
        String url = replaceUrl(callbackUrl, LOCAL_HOST_NAME, RESOLVED_LOCAL_HOST_NAME) + "/kbnlu/api/config/" + endPoint;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), payload);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            if (response.code() != 200) {
                System.out.println("Call to endPoint " + url + " failed with status code : " + response.code());
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, JsonObject.class);
        } catch (Exception exception) {
            System.out.println("Error in http call: " + exception.getMessage());
        }
        return null;
    }
    public static String replaceUrl(String url, String oldHostname, String newHostname) {
        try {
            URI uri = new URI(url);

            if (uri.getHost().equals(oldHostname)) {
                String scheme = "http";
                String newAuthority = newHostname;

                URI newUri = new URI(
                        scheme,
                        newAuthority,
                        uri.getPath(),
                        uri.getQuery(),
                        uri.getFragment()
                );

                return newUri.toString();
            } else {
                return url;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}