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

package app.krista.extensions.model;

import java.util.Objects;

public class ServiceConfig {

    private final String callbackUrl;
    private final ServiceType serviceType;

    public ServiceConfig(String callbackUrl, ServiceType serviceType) {
        this.callbackUrl = callbackUrl;
        this.serviceType = serviceType;
    }

    public ServiceType getModelType() {
        return serviceType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceConfig that = (ServiceConfig) o;
        return Objects.equals(callbackUrl, that.callbackUrl) && serviceType == that.serviceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(callbackUrl, serviceType);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "callbackUrl='" + callbackUrl + '\'' +
                ", modelType=" + serviceType +
                '}';
    }

}
