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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ServiceMap class.
 * Tests all public methods including addConnection, getConnections, and removeConnection.
 */
class ServiceMapTest {

    private ServiceMap serviceMap;

    @BeforeEach
    void setUp() {
        serviceMap = new ServiceMap();
    }

    /**
     * Test getConnections returns empty set initially.
     */
    @Test
    void testGetConnections_InitiallyEmpty() {
        // Act
        Set<ServiceConfig> connections = serviceMap.getConnections();

        // Assert
        assertNotNull(connections);
        assertTrue(connections.isEmpty());
        assertEquals(0, connections.size());
    }

    /**
     * Test addConnection with DEFAULT service type.
     */
    @Test
    void testAddConnection_WithDefaultType() {
        // Arrange
        String url = "http://example.com";

        // Act
        serviceMap.addConnection(url, ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test addConnection with FALLBACK service type.
     */
    @Test
    void testAddConnection_WithFallbackType() {
        // Arrange
        String url = "http://fallback.com";

        // Act
        serviceMap.addConnection(url, ServiceType.FALLBACK);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test addConnection with multiple connections.
     */
    @Test
    void testAddConnection_MultipleConnections() {
        // Arrange & Act
        serviceMap.addConnection("http://example1.com", ServiceType.DEFAULT);
        serviceMap.addConnection("http://example2.com", ServiceType.FALLBACK);
        serviceMap.addConnection("http://example3.com", ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(3, connections.size());
    }

    /**
     * Test addConnection with duplicate connections.
     * Since ServiceConfig uses equals/hashCode, duplicates should not be added to Set.
     */
    @Test
    void testAddConnection_DuplicateConnections() {
        // Arrange
        String url = "http://example.com";
        ServiceType type = ServiceType.DEFAULT;

        // Act
        serviceMap.addConnection(url, type);
        serviceMap.addConnection(url, type);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test addConnection with same URL but different service types.
     */
    @Test
    void testAddConnection_SameUrlDifferentTypes() {
        // Arrange
        String url = "http://example.com";

        // Act
        serviceMap.addConnection(url, ServiceType.DEFAULT);
        serviceMap.addConnection(url, ServiceType.FALLBACK);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(2, connections.size());
    }

    /**
     * Test removeConnection removes existing connection.
     */
    @Test
    void testRemoveConnection_ExistingConnection() {
        // Arrange
        String url = "http://example.com";
        ServiceType type = ServiceType.DEFAULT;
        serviceMap.addConnection(url, type);

        // Act
        serviceMap.removeConnection(url, type);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertTrue(connections.isEmpty());
    }

    /**
     * Test removeConnection with non-existing connection.
     */
    @Test
    void testRemoveConnection_NonExistingConnection() {
        // Arrange
        serviceMap.addConnection("http://example1.com", ServiceType.DEFAULT);

        // Act
        serviceMap.removeConnection("http://example2.com", ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test removeConnection from empty map.
     */
    @Test
    void testRemoveConnection_FromEmptyMap() {
        // Act
        serviceMap.removeConnection("http://example.com", ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertTrue(connections.isEmpty());
    }

    /**
     * Test removeConnection with wrong service type.
     */
    @Test
    void testRemoveConnection_WrongServiceType() {
        // Arrange
        String url = "http://example.com";
        serviceMap.addConnection(url, ServiceType.DEFAULT);

        // Act
        serviceMap.removeConnection(url, ServiceType.FALLBACK);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test removeConnection removes only matching connection.
     */
    @Test
    void testRemoveConnection_RemovesOnlyMatching() {
        // Arrange
        serviceMap.addConnection("http://example1.com", ServiceType.DEFAULT);
        serviceMap.addConnection("http://example2.com", ServiceType.FALLBACK);
        serviceMap.addConnection("http://example3.com", ServiceType.DEFAULT);

        // Act
        serviceMap.removeConnection("http://example2.com", ServiceType.FALLBACK);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(2, connections.size());
    }

    /**
     * Test getConnections returns actual set (not a copy).
     */
    @Test
    void testGetConnections_ReturnsSameInstance() {
        // Act
        Set<ServiceConfig> connections1 = serviceMap.getConnections();
        Set<ServiceConfig> connections2 = serviceMap.getConnections();

        // Assert
        assertSame(connections1, connections2);
    }

    /**
     * Test addConnection with null URL.
     */
    @Test
    void testAddConnection_WithNullUrl() {
        // Act
        serviceMap.addConnection(null, ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test addConnection with empty URL.
     */
    @Test
    void testAddConnection_WithEmptyUrl() {
        // Act
        serviceMap.addConnection("", ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(1, connections.size());
    }

    /**
     * Test multiple add and remove operations.
     */
    @Test
    void testMultipleAddAndRemoveOperations() {
        // Arrange & Act
        serviceMap.addConnection("http://example1.com", ServiceType.DEFAULT);
        serviceMap.addConnection("http://example2.com", ServiceType.FALLBACK);
        assertEquals(2, serviceMap.getConnections().size());

        serviceMap.removeConnection("http://example1.com", ServiceType.DEFAULT);
        assertEquals(1, serviceMap.getConnections().size());

        serviceMap.addConnection("http://example3.com", ServiceType.DEFAULT);
        assertEquals(2, serviceMap.getConnections().size());

        serviceMap.removeConnection("http://example2.com", ServiceType.FALLBACK);
        serviceMap.removeConnection("http://example3.com", ServiceType.DEFAULT);
        assertEquals(0, serviceMap.getConnections().size());
    }

    /**
     * Test that connections set is a HashSet (order not guaranteed).
     */
    @Test
    void testGetConnections_IsSet() {
        // Arrange
        serviceMap.addConnection("http://example1.com", ServiceType.DEFAULT);
        serviceMap.addConnection("http://example2.com", ServiceType.FALLBACK);

        // Act
        Set<ServiceConfig> connections = serviceMap.getConnections();

        // Assert
        assertNotNull(connections);
        assertTrue(connections instanceof Set);
    }

    /**
     * Test addConnection with complex URLs.
     */
    @Test
    void testAddConnection_WithComplexUrls() {
        // Arrange & Act
        serviceMap.addConnection("https://api.example.com:8080/v1/callback?key=value", ServiceType.DEFAULT);
        serviceMap.addConnection("http://localhost:3000/webhook", ServiceType.FALLBACK);
        serviceMap.addConnection("https://secure.example.com/api/v2/notify", ServiceType.DEFAULT);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertEquals(3, connections.size());
    }

    /**
     * Test that added connections can be verified.
     */
    @Test
    void testAddConnection_CanVerifyContent() {
        // Arrange
        String url = "http://example.com";
        ServiceType type = ServiceType.DEFAULT;

        // Act
        serviceMap.addConnection(url, type);

        // Assert
        Set<ServiceConfig> connections = serviceMap.getConnections();
        boolean found = connections.stream()
                .anyMatch(config -> url.equals(config.getCallbackUrl()) && type.equals(config.getModelType()));
        assertTrue(found);
    }

    /**
     * Test removeConnection after multiple adds of same connection.
     */
    @Test
    void testRemoveConnection_AfterMultipleAdds() {
        // Arrange
        String url = "http://example.com";
        ServiceType type = ServiceType.DEFAULT;
        serviceMap.addConnection(url, type);
        serviceMap.addConnection(url, type);
        serviceMap.addConnection(url, type);

        // Act
        serviceMap.removeConnection(url, type);

        // Assert - should remove the single instance
        Set<ServiceConfig> connections = serviceMap.getConnections();
        assertTrue(connections.isEmpty());
    }
}

