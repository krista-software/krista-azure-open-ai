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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ChatGPTResponse class.
 * Tests all public methods including getters and getAnswer.
 */
class ChatGPTResponseTest {

    /**
     * Test getAnswer with valid choices.
     */
    @Test
    void testGetAnswer_WithValidChoices() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.content = "This is the answer";
        message.role = "assistant";
        choice.message = message;
        
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice);
        response.choices = choices;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals("This is the answer", answer);
    }

    /**
     * Test getAnswer with null choices.
     */
    @Test
    void testGetAnswer_WithNullChoices() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.choices = null;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals("N/A", answer);
    }

    /**
     * Test getAnswer with empty choices list.
     */
    @Test
    void testGetAnswer_WithEmptyChoices() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.choices = new ArrayList<>();

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals("N/A", answer);
    }

    /**
     * Test getAnswer with multiple choices returns first.
     */
    @Test
    void testGetAnswer_WithMultipleChoices() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        
        ChatGPTResponse.Choice choice1 = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message1 = new ChatGPTResponse.Choice.Message();
        message1.content = "First answer";
        choice1.message = message1;
        
        ChatGPTResponse.Choice choice2 = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message2 = new ChatGPTResponse.Choice.Message();
        message2.content = "Second answer";
        choice2.message = message2;
        
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        response.choices = choices;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals("First answer", answer);
    }

    /**
     * Test getId getter.
     */
    @Test
    void testGetId() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.id = "chatcmpl-123";

        // Act
        String id = response.getId();

        // Assert
        assertEquals("chatcmpl-123", id);
    }

    /**
     * Test getObject getter.
     */
    @Test
    void testGetObject() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.object = "chat.completion";

        // Act
        String object = response.getObject();

        // Assert
        assertEquals("chat.completion", object);
    }

    /**
     * Test getCreated getter.
     */
    @Test
    void testGetCreated() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.created = 1234567890L;

        // Act
        long created = response.getCreated();

        // Assert
        assertEquals(1234567890L, created);
    }

    /**
     * Test getModel getter.
     */
    @Test
    void testGetModel() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.model = "gpt-4";

        // Act
        String model = response.getModel();

        // Assert
        assertEquals("gpt-4", model);
    }

    /**
     * Test getChoices getter.
     */
    @Test
    void testGetChoices() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        response.choices = choices;

        // Act
        List<ChatGPTResponse.Choice> result = response.getChoices();

        // Assert
        assertSame(choices, result);
    }

    /**
     * Test Choice.getMessage getter.
     */
    @Test
    void testChoice_GetMessage() {
        // Arrange
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        choice.message = message;

        // Act
        ChatGPTResponse.Choice.Message result = choice.getMessage();

        // Assert
        assertSame(message, result);
    }

    /**
     * Test Choice.getFinishReason getter.
     */
    @Test
    void testChoice_GetFinishReason() {
        // Arrange
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        choice.finish_reason = "stop";

        // Act
        String finishReason = choice.getFinishReason();

        // Assert
        assertEquals("stop", finishReason);
    }

    /**
     * Test Choice.getIndex getter.
     */
    @Test
    void testChoice_GetIndex() {
        // Arrange
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        choice.index = 0;

        // Act
        int index = choice.getIndex();

        // Assert
        assertEquals(0, index);
    }

    /**
     * Test Message.getRole getter.
     */
    @Test
    void testMessage_GetRole() {
        // Arrange
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.role = "assistant";

        // Act
        String role = message.getRole();

        // Assert
        assertEquals("assistant", role);
    }

    /**
     * Test Message.getContent getter.
     */
    @Test
    void testMessage_GetContent() {
        // Arrange
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.content = "Hello, how can I help you?";

        // Act
        String content = message.getContent();

        // Assert
        assertEquals("Hello, how can I help you?", content);
    }

    /**
     * Test toString methods are not null.
     */
    @Test
    void testToString_NotNull() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();

        // Act & Assert
        assertNotNull(response.toString());
        assertNotNull(choice.toString());
        assertNotNull(message.toString());
    }

    /**
     * Test complete response structure.
     */
    @Test
    void testCompleteResponseStructure() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        response.id = "chatcmpl-123";
        response.object = "chat.completion";
        response.created = 1234567890L;
        response.model = "gpt-4";

        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        choice.index = 0;
        choice.finish_reason = "stop";

        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.role = "assistant";
        message.content = "Complete answer";

        choice.message = message;
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice);
        response.choices = choices;

        // Act & Assert
        assertEquals("chatcmpl-123", response.getId());
        assertEquals("chat.completion", response.getObject());
        assertEquals(1234567890L, response.getCreated());
        assertEquals("gpt-4", response.getModel());
        assertEquals(1, response.getChoices().size());
        assertEquals("Complete answer", response.getAnswer());
        assertEquals("assistant", response.getChoices().get(0).getMessage().getRole());
        assertEquals("stop", response.getChoices().get(0).getFinishReason());
        assertEquals(0, response.getChoices().get(0).getIndex());
    }

    /**
     * Test getAnswer with null message content.
     */
    @Test
    void testGetAnswer_WithNullMessageContent() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.content = null;
        choice.message = message;
        
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice);
        response.choices = choices;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertNull(answer);
    }

    /**
     * Test getAnswer with empty message content.
     */
    @Test
    void testGetAnswer_WithEmptyMessageContent() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        message.content = "";
        choice.message = message;
        
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice);
        response.choices = choices;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals("", answer);
    }

    /**
     * Test with long message content.
     */
    @Test
    void testGetAnswer_WithLongContent() {
        // Arrange
        ChatGPTResponse response = new ChatGPTResponse();
        ChatGPTResponse.Choice choice = new ChatGPTResponse.Choice();
        ChatGPTResponse.Choice.Message message = new ChatGPTResponse.Choice.Message();
        
        StringBuilder longContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longContent.append("This is a long answer. ");
        }
        message.content = longContent.toString();
        choice.message = message;
        
        List<ChatGPTResponse.Choice> choices = new ArrayList<>();
        choices.add(choice);
        response.choices = choices;

        // Act
        String answer = response.getAnswer();

        // Assert
        assertEquals(longContent.toString(), answer);
        assertTrue(answer.length() > 1000);
    }
}

