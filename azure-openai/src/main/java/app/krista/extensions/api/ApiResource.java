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

package app.krista.extensions.api;


import app.krista.extensions.model.ServiceType;
import app.krista.extensions.model.ServiceMap;
import app.krista.extensions.store.ServiceStore;
import com.google.gson.JsonObject;
import org.jvnet.hk2.annotations.Service;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;
@Service
@Path("/")
public class ApiResource {
    private final ServiceStore serviceStore;
    @Inject
    public ApiResource(ServiceStore serviceStore) {
        this.serviceStore = serviceStore;
    }
    @POST
    @Path("/register")
    @Produces("application/json")
    @Consumes("application/json")
    public Map<String, Object> registerService(JsonObject payload) {
        String callbackUrl = payload.get("callback").getAsString();
        ServiceType serviceType = ServiceType.valueOf(payload.get("modelType").getAsString());
        ServiceMap serviceMap = serviceStore.get();
        serviceMap.addConnection(callbackUrl, serviceType);
        serviceStore.put(serviceMap);
        Map<String,Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
}
