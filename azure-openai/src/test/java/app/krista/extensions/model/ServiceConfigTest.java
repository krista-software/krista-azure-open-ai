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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ServiceConfig class.
 * Tests all public methods including getters, equals, hashCode, and toString.
 */
class ServiceConfigTest {

    /**
     * Test constructor and getters with DEFAULT service type.
     */
    @Test
    void testConstructorAndGetters_WithDefaultType() {
        // Arrange
        String url = "http://example.com/callback";
        ServiceType type = ServiceType.DEFAULT;

        // Act
        ServiceConfig config = new ServiceConfig(url, type);

        // Assert
        assertEquals(url, config.getCallbackUrl());
        assertEquals(type, config.getModelType());
    }

    /**
     * Test constructor and getters with FALLBACK service type.
     */
    @Test
    void testConstructorAndGetters_WithFallbackType() {
        // Arrange
        String url = "http://fallback.com/callback";
        ServiceType type = ServiceType.FALLBACK;

        // Act
        ServiceConfig config = new ServiceConfig(url, type);

        // Assert
        assertEquals(url, config.getCallbackUrl());
        assertEquals(type, config.getModelType());
    }

    /**
     * Test equals method with same object.
     */
    @Test
    void testEquals_SameObject() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act & Assert
        assertEquals(config, config);
    }

    /**
     * Test equals method with equal objects.
     */
    @Test
    void testEquals_EqualObjects() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act & Assert
        assertEquals(config1, config2);
        assertEquals(config2, config1);
    }

    /**
     * Test equals method with different URLs.
     */
    @Test
    void testEquals_DifferentUrls() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig("http://example1.com", ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example2.com", ServiceType.DEFAULT);

        // Act & Assert
        assertNotEquals(config1, config2);
    }

    /**
     * Test equals method with different service types.
     */
    @Test
    void testEquals_DifferentServiceTypes() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example.com", ServiceType.FALLBACK);

        // Act & Assert
        assertNotEquals(config1, config2);
    }

    /**
     * Test equals method with null.
     */
    @Test
    void testEquals_WithNull() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act & Assert
        assertNotEquals(config, null);
    }

    /**
     * Test equals method with different class.
     */
    @Test
    void testEquals_WithDifferentClass() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.DEFAULT);
        String other = "not a ServiceConfig";

        // Act & Assert
        assertNotEquals(config, other);
    }

    /**
     * Test hashCode consistency.
     */
    @Test
    void testHashCode_Consistency() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act
        int hash1 = config.hashCode();
        int hash2 = config.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }

    /**
     * Test hashCode for equal objects.
     */
    @Test
    void testHashCode_EqualObjects() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act & Assert
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    /**
     * Test hashCode for different objects.
     */
    @Test
    void testHashCode_DifferentObjects() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig("http://example1.com", ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example2.com", ServiceType.DEFAULT);

        // Act & Assert
        assertNotEquals(config1.hashCode(), config2.hashCode());
    }

    /**
     * Test toString method contains expected information.
     */
    @Test
    void testToString_ContainsUrl() {
        // Arrange
        String url = "http://example.com";
        ServiceConfig config = new ServiceConfig(url, ServiceType.DEFAULT);

        // Act
        String result = config.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains(url));
    }

    /**
     * Test toString method contains service type.
     */
    @Test
    void testToString_ContainsServiceType() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.FALLBACK);

        // Act
        String result = config.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("FALLBACK"));
    }

    /**
     * Test toString method is not empty.
     */
    @Test
    void testToString_NotEmpty() {
        // Arrange
        ServiceConfig config = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act
        String result = config.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    /**
     * Test with null URL.
     */
    @Test
    void testConstructor_WithNullUrl() {
        // Arrange & Act
        ServiceConfig config = new ServiceConfig(null, ServiceType.DEFAULT);

        // Assert
        assertNull(config.getCallbackUrl());
        assertEquals(ServiceType.DEFAULT, config.getModelType());
    }

    /**
     * Test with empty URL.
     */
    @Test
    void testConstructor_WithEmptyUrl() {
        // Arrange
        String url = "";

        // Act
        ServiceConfig config = new ServiceConfig(url, ServiceType.DEFAULT);

        // Assert
        assertEquals(url, config.getCallbackUrl());
        assertTrue(config.getCallbackUrl().isEmpty());
    }

    /**
     * Test with complex URL.
     */
    @Test
    void testConstructor_WithComplexUrl() {
        // Arrange
        String url = "https://api.example.com:8080/v1/callback?param=value&key=123";

        // Act
        ServiceConfig config = new ServiceConfig(url, ServiceType.DEFAULT);

        // Assert
        assertEquals(url, config.getCallbackUrl());
    }

    /**
     * Test equals with both null URLs.
     */
    @Test
    void testEquals_BothNullUrls() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig(null, ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig(null, ServiceType.DEFAULT);

        // Act & Assert
        assertEquals(config1, config2);
    }

    /**
     * Test equals with one null URL.
     */
    @Test
    void testEquals_OneNullUrl() {
        // Arrange
        ServiceConfig config1 = new ServiceConfig(null, ServiceType.DEFAULT);
        ServiceConfig config2 = new ServiceConfig("http://example.com", ServiceType.DEFAULT);

        // Act & Assert
        assertNotEquals(config1, config2);
        assertNotEquals(config2, config1);
    }
}

