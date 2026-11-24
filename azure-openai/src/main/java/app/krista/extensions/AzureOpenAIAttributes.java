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

import app.krista.extension.util.InvokerAttributeProvider;
import app.krista.extensions.util.OpenAIConstants;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Named;

@Service
public class AzureOpenAIAttributes {
    @Inject
    @Named(OpenAIConstants.API_KEY)
    private InvokerAttributeProvider<String> apiKey;
    @Inject
    @Named(OpenAIConstants.MODEL)
    private InvokerAttributeProvider<String> modelName;

    @Inject
    @Named(OpenAIConstants.SERVICE_URL)
    private InvokerAttributeProvider<String> serviceUrl;

    public String getApiKey() {
        return apiKey.get();
    }

    public String getModelName() {
        return modelName.get();
    }

    public String getServiceUrl() {
        return serviceUrl.get();
    }
}
