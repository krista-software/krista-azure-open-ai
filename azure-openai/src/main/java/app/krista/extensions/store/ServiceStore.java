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

package app.krista.extensions.store;

import app.krista.extensions.model.ServiceMap;
import app.krista.extensions.util.KeyValueStore;
import com.google.gson.Gson;
import org.jvnet.hk2.annotations.Service;
import javax.inject.Inject;

@Service
public class ServiceStore {

    private final KeyValueStore keyValueStore;
    private static final String REGISTRY = "registry";
    private final Gson gson = new Gson();

    @Inject
    public ServiceStore(KeyValueStore keyValueStore) {
        this.keyValueStore = keyValueStore;
    }

    public void put(ServiceMap registry) {
        keyValueStore.put(REGISTRY, gson.toJson(registry));
    }

    public ServiceMap get() {
        ServiceMap serviceMap = gson.fromJson((String) keyValueStore.get(REGISTRY), ServiceMap.class);
        if (serviceMap == null) {
            serviceMap = new ServiceMap();
        }
        return serviceMap;
    }

    public void remove() {
        keyValueStore.remove(REGISTRY);
    }

}
