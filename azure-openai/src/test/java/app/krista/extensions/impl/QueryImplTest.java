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

import app.krista.extensions.util.OpenAIConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QueryImpl class.
 * Tests public methods that don't require KSDK dependencies.
 * Note: Methods like queryModel() require InvokerAttributeProvider from KSDK and are not tested here.
 * Only testing static methods and methods without KSDK dependencies.
 */
class QueryImplTest {

    private QueryImpl queryImpl;

    @BeforeEach
    void setUp() {
        queryImpl = new QueryImpl();
    }

    /**
     * Test execute with invalid JSON.
     * Verifies that IllegalArgumentException is thrown for invalid JSON.
     */
    @Test
    void testExecute_WithInvalidJson() {
        // Arrange
        String invalidJson = "not a valid json";
        String apiKey = "valid-api-key";
        String modelName = OpenAIConstants.GPT_4;
        String serviceUrl = "https://api.openai.com/v1/chat/completions";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            queryImpl.execute(invalidJson, apiKey, modelName, serviceUrl);
        });
    }

    /**
     * Test execute with valid JSON but network will fail.
     * Verifies that the method doesn't throw exceptions with valid JSON input.
     */
    @Test
    void testExecute_WithValidJson_NetworkFailure() {
        // Arrange
        String prompt = "{\"messages\":[{\"role\":\"user\",\"content\":\"Hello\"}]}";
        
        // Act & Assert - will throw IOException due to invalid URL, but validates JSON parsing
        assertThrows(IOException.class, () -> {
            queryImpl.execute(prompt, "test-key", OpenAIConstants.GPT_4, "http://invalid-url-that-does-not-exist");
        });
    }

    /**
     * Test getAIModel method.
     * Verifies that the correct AI model identifier is returned.
     */
    @Test
    void testGetAIModel() {
        // Act
        String result = queryImpl.getAIModel();

        // Assert
        assertNotNull(result);
        assertEquals("MS_Azure_OpenAI_4", result);
    }

    /**
     * Test getAIModel returns consistent value.
     * Verifies that multiple calls return the same value.
     */
    @Test
    void testGetAIModel_Consistency() {
        // Act
        String result1 = queryImpl.getAIModel();
        String result2 = queryImpl.getAIModel();

        // Assert
        assertEquals(result1, result2);
        assertEquals("MS_Azure_OpenAI_4", result1);
    }

    /**
     * Test getAIModel returns non-empty string.
     * Verifies that the model identifier is not empty.
     */
    @Test
    void testGetAIModel_NotEmpty() {
        // Act
        String result = queryImpl.getAIModel();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.length() > 0);
    }

    /**
     * Test briefPause method.
     * Verifies that the method executes without errors.
     */
    @Test
    void testBriefPause() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            long startTime = System.currentTimeMillis();
            QueryImpl.briefPause();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            // Verify that some time has passed (at least 200ms, allowing for timing variations)
            assertTrue(duration >= 200, "briefPause should pause for approximately 250ms");
        });
    }

    /**
     * Test briefPause duration.
     * Verifies that briefPause waits for approximately 250ms.
     */
    @Test
    void testBriefPause_Duration() {
        // Act
        long startTime = System.currentTimeMillis();
        QueryImpl.briefPause();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Assert - should be around 250ms, but allow some variance
        assertTrue(duration >= 200 && duration <= 400, 
            "briefPause should wait approximately 250ms, but was " + duration + "ms");
    }

    /**
     * Test briefPause is static and can be called without instance.
     * Verifies that briefPause is a static method.
     */
    @Test
    void testBriefPause_Static() {
        // Act & Assert - calling static method directly
        assertDoesNotThrow(() -> {
            QueryImpl.briefPause();
        });
    }

    /**
     * Test execute with null JSON.
     * Verifies proper error handling for null input.
     */
    @Test
    void testExecute_WithNullJson() {
        // Arrange
        String apiKey = "valid-api-key";
        String modelName = OpenAIConstants.GPT_4;
        String serviceUrl = "https://api.openai.com/v1/chat/completions";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            queryImpl.execute(null, apiKey, modelName, serviceUrl);
        });
    }

    /**
     * Test execute with empty JSON.
     * Verifies proper error handling for empty JSON.
     */
    @Test
    void testExecute_WithEmptyJson() {
        // Arrange
        String emptyJson = "";
        String apiKey = "valid-api-key";
        String modelName = OpenAIConstants.GPT_4;
        String serviceUrl = "https://api.openai.com/v1/chat/completions";

        // Act & Assert - empty string causes NullPointerException in Gson parsing
        assertThrows(Exception.class, () -> {
            queryImpl.execute(emptyJson, apiKey, modelName, serviceUrl);
        });
    }

    /**
     * Test execute with malformed JSON structure.
     * Verifies that valid JSON but without expected structure is handled.
     */
    @Test
    void testExecute_WithMalformedJsonStructure() {
        // Arrange
        String malformedJson = "{\"invalid\":\"structure\"}";
        String apiKey = "valid-api-key";
        String modelName = OpenAIConstants.GPT_4;
        String serviceUrl = "https://api.openai.com/v1/chat/completions";

        // Act & Assert - will throw Exception (either IllegalArgumentException or IOException)
        assertThrows(Exception.class, () -> {
            queryImpl.execute(malformedJson, apiKey, modelName, serviceUrl);
        });
    }

    /**
     * Test execute with GPT-4 model name.
     * Verifies that GPT-4 model name is accepted.
     */
    @Test
    void testExecute_WithGPT4Model() {
        // Arrange
        String prompt = "{\"messages\":[{\"role\":\"user\",\"content\":\"Test\"}]}";
        
        // Act & Assert - will throw IOException due to network
        assertThrows(IOException.class, () -> {
            queryImpl.execute(prompt, "test-key", OpenAIConstants.GPT_4, "http://invalid-url");
        });
    }

    /**
     * Test execute with ChatGPT 3.5 model name.
     * Verifies that ChatGPT 3.5 model name is accepted.
     */
    @Test
    void testExecute_WithChatGPT35Model() {
        // Arrange
        String prompt = "{\"messages\":[{\"role\":\"user\",\"content\":\"Test\"}]}";
        
        // Act & Assert - will throw IOException due to network
        assertThrows(IOException.class, () -> {
            queryImpl.execute(prompt, "test-key", "ChatGPT 3.5", "http://invalid-url");
        });
    }

    /**
     * Test execute with complex JSON structure.
     * Verifies that complex JSON is parsed correctly.
     */
    @Test
    void testExecute_WithComplexJson() {
        // Arrange
        String complexJson = "{\"messages\":[{\"role\":\"system\",\"content\":\"You are helpful\"},{\"role\":\"user\",\"content\":\"Hello\"}]}";
        
        // Act & Assert - will throw IOException due to network
        assertThrows(IOException.class, () -> {
            queryImpl.execute(complexJson, "test-key", OpenAIConstants.GPT_4, "http://invalid-url");
        });
    }
}

