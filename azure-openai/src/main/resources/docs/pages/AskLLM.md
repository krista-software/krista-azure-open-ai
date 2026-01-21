# Ask LLM

## Overview

Queries the configured Azure OpenAI language model with a given prompt. This catalog request accepts JSON-formatted input containing messages and conversation history, enabling both single queries and multi-turn conversations with the AI model.

## Request Details

- **Area**: Query
- **Type**: QUERY_SYSTEM
- **Retry Support**: ❌ No (Rate limiting and connection management handled internally)

## Input Parameters

| Parameter Name | Type | Required | Description | Example |
|----------------|------|----------|-------------|---------|
| Prompt | Text | Yes | JSON string containing messages for the language model | `{"messages": [{"role":"user", "content": "What is AI?"}]}` |

### Parameter Details

#### Prompt
The prompt parameter must be a valid JSON string containing a `messages` array. Each message object should include:

- **role**: The role of the message sender
  - `"system"`: System instructions or context
  - `"user"`: User input or questions
  - `"assistant"`: Previous AI responses (for conversation history)
- **content**: The actual message content as a string

**Validation Rules**:
- Must be valid JSON format
- Must contain a "messages" array
- Each message must have "role" and "content" properties
- Content cannot be empty or null

## Output Parameters

| Parameter Name | Type | Description |
|----------------|------|-------------|
| LLM Response | Text | The generated response from the Azure OpenAI model |

## Validation Rules

| Validation | Error Message | Resolution |
|------------|---------------|------------|
| Invalid JSON Format | "The prompt must be in valid JSON format with a 'messages' array. Example: {\"messages\": [{\"role\": \"user\", \"content\": \"Your question here\"}]}. Please check your JSON syntax and try again." | Validate JSON syntax and ensure proper structure |
| Missing API Key | "Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource." | Configure extension with valid Azure OpenAI API key |
| Missing Model | "AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration." | Select a model in extension configuration |

## Error Handling

### Input Errors (INPUT_ERROR)
**Cause**: Invalid or malformed prompt parameter
**Error Message**: "The prompt must be in valid JSON format with a 'messages' array. Example: {\"messages\": [{\"role\": \"user\", \"content\": \"Your question here\"}]}. Please check your JSON syntax and try again."
**Common Scenarios**:
- Invalid JSON syntax in prompt
- Missing 'messages' array
- Malformed message structure
- Incorrect data types

**Resolution**: Validate JSON format using a JSON validator and ensure the prompt follows the required structure with a 'messages' array containing properly formatted message objects.

### Logic Errors (LOGIC_ERROR)
**Cause**: Extension configuration issues
**Error Messages**:
- "Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource."
- "AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration."

**Common Scenarios**:
- API key not configured or empty
- Model not selected in configuration
- Service URL not properly set

**Resolution**: Complete the extension configuration by providing all required parameters: API Key, Model selection, and Service URL.

### System Errors (SYSTEM_ERROR)
**Cause**: Azure OpenAI service or network connectivity issues
**Error Messages**:
- "The Azure OpenAI service is currently experiencing high demand or connectivity issues. Please wait a moment and try your request again."
- "We encountered an unexpected issue while processing your request. This may be due to a temporary service interruption."

**Common Scenarios**:
- Azure OpenAI service temporarily unavailable
- Network connectivity problems
- Rate limiting exceeded
- Service outages

**Resolution**: Wait a moment and retry your request. If the issue persists, check Azure service status, verify network connectivity, and ensure your API credentials are valid.

## Usage Examples

### Example 1: Simple Question

**Input**:
```json
{
  "messages": [
    {
      "role": "user",
      "content": "What is artificial intelligence?"
    }
  ]
}
```

**Output**:
```
Artificial intelligence (AI) refers to the simulation of human intelligence in machines that are programmed to think and learn like humans. It encompasses various technologies including machine learning, natural language processing, and computer vision.
```

**Result**: Single-turn conversation with a straightforward question about AI.

### Example 2: Conversation with Context

**Input**:
```json
{
  "messages": [
    {
      "role": "system",
      "content": "You are a helpful assistant specializing in technology."
    },
    {
      "role": "user",
      "content": "Explain machine learning"
    },
    {
      "role": "assistant",
      "content": "Machine learning is a subset of AI that enables computers to learn and improve from experience without being explicitly programmed."
    },
    {
      "role": "user",
      "content": "What are the main types?"
    }
  ]
}
```

**Output**:
```
The main types of machine learning are:
1. Supervised Learning - Uses labeled data to train models
2. Unsupervised Learning - Finds patterns in unlabeled data
3. Reinforcement Learning - Learns through interaction and feedback
```

**Result**: Multi-turn conversation with context and conversation history.

### Example 3: System Instructions

**Input**:
```json
{
  "messages": [
    {
      "role": "system",
      "content": "You are a concise technical writer. Provide brief, accurate answers."
    },
    {
      "role": "user",
      "content": "Define API"
    }
  ]
}
```

**Output**:
```
API (Application Programming Interface): A set of protocols and tools that allows different software applications to communicate with each other.
```

**Result**: Response follows system instructions for concise, technical writing style.

## Business Rules

1. **Rate Limiting**: Requests are automatically rate-limited to prevent exceeding Azure OpenAI quotas
2. **Token Management**: Input and output tokens are managed within Azure OpenAI limits
3. **Model Selection**: Uses the model specified in extension configuration (GPT-3.5 or GPT-4)
4. **Context Window**: Respects model-specific context window limitations
5. **Content Filtering**: Azure OpenAI content filtering policies apply to all requests
6. **Conversation History**: Maintains conversation context within the same request only

## Limitations

1. **Context Length**: Limited by the selected model's context window (4K-32K tokens)
2. **Response Length**: Maximum response limited to 512 tokens by default
3. **Rate Limits**: Subject to Azure OpenAI service rate limits
4. **Content Restrictions**: Subject to Azure OpenAI content filtering policies
5. **Session State**: No persistent conversation state between separate requests
6. **Model Availability**: Limited to models deployed in your Azure OpenAI resource

## Best Practices

### 1. Optimize Prompt Design
Structure prompts clearly with appropriate roles and context to get better responses.

### 2. Manage Conversation Length
Keep conversation history concise to stay within token limits and reduce costs.

### 3. Use System Messages
Leverage system messages to set context and behavior expectations for the AI.

### 4. Handle Rate Limits
Implement appropriate delays between requests if making multiple calls.

### 5. Validate JSON Format
Always validate JSON format before sending to prevent input errors.

## Common Use Cases

### 1. Question Answering
```
Scenario: User asks factual questions
Action: Send question as user message
Result: Receive informative answer from AI
```

### 2. Content Generation
```
Scenario: Generate creative or technical content
Action: Provide clear instructions and context
Result: Receive generated content based on requirements
```

### 3. Conversation Assistance
```
Scenario: Multi-turn conversation with context
Action: Include conversation history in messages array
Result: Contextually aware responses maintaining conversation flow
```

### 4. Code Assistance
```
Scenario: Programming help and code generation
Action: Describe coding problem or requirements
Result: Receive code examples, explanations, or debugging help
```

## Related Catalog Requests

- [Get AI Model Name](/pages/GetAIModelName.md) - Retrieve the configured model information

## Technical Implementation

### Helper Class
- **Class**: QueryImpl
- **Package**: app.krista.extensions.impl
- **Validation**: JSON format validation and message structure verification
- **Service**: Direct integration with Azure OpenAI REST API

### Telemetry Metrics
- **REQUEST_COUNT**: Number of LLM requests processed
- **RESPONSE_TIME**: Time taken to process requests
- **ERROR_COUNT**: Number of failed requests
- **TOKEN_USAGE**: Token consumption tracking

## Troubleshooting

### JSON Format Issues
**Error**: "The prompt must be in valid JSON format with a 'messages' array..."
**Cause**: Malformed JSON in prompt parameter
**Solution**:
1. Use a JSON validator to check your prompt syntax
2. Ensure the prompt contains a 'messages' array
3. Verify each message has 'role' and 'content' properties
4. Use the provided example format as a template

### Configuration Issues
**Error**: "Azure OpenAI API Key is not configured..." or "AI Model is not selected..."
**Cause**: Incomplete extension configuration
**Solution**:
1. Navigate to extension configuration
2. Provide a valid Azure OpenAI API key
3. Select either 'ChatGPT 3.5' or 'ChatGPT 4'
4. Ensure Service URL is properly formatted
5. Test the connection after configuration

### Service Connectivity Issues
**Error**: "The Azure OpenAI service is currently experiencing high demand..."
**Cause**: Azure OpenAI service or network issues
**Solution**:
1. Wait 30-60 seconds and retry your request
2. Check Azure service status at status.azure.com
3. Verify your network allows HTTPS connections to Azure
4. Confirm your API key is still valid and active
5. Check if your Azure OpenAI resource has sufficient quota

### Model Deployment Issues
**Error**: Connection test failures during configuration
**Cause**: Selected model not deployed or accessible in Azure resource
**Solution**:
1. Log into Azure portal
2. Navigate to your Azure OpenAI resource
3. Check "Model deployments" section
4. Ensure the selected model (GPT-3.5 or GPT-4) is deployed
5. Verify deployment status is "Succeeded"
6. Match the model selection in Krista with your Azure deployment

## See Also

- [Extension Configuration](/pages/ExtensionConfiguration.md) - Configure Azure OpenAI connection
- [Authentication](/pages/Authentication.md) - Understand authentication requirements
- [Get AI Model Name](/pages/GetAIModelName.md) - Related catalog request
