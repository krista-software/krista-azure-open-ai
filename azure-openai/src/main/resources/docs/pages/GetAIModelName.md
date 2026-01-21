# Get AI Model Name

## Overview

Returns the name of the Azure OpenAI language model that is currently configured and being used by the extension. This catalog request provides information about which AI model will process queries.

## Request Details

- **Area**: Query
- **Type**: QUERY_SYSTEM
- **Retry Support**: ✅ Yes (Simple information retrieval with no side effects)

## Input Parameters

This catalog request does not require any input parameters.

| Parameter Name | Type | Required | Description | Example |
|----------------|------|----------|-------------|---------|
| N/A | N/A | N/A | No input parameters required | N/A |

## Output Parameters

| Parameter Name | Type | Description |
|----------------|------|-------------|
| AI Model | Text | The name identifier of the configured Azure OpenAI model |

### Output Details

#### AI Model
The returned model name indicates which Azure OpenAI model is configured:

- **Format**: String identifier for the AI model
- **Values**: Returns a standardized model identifier
- **Purpose**: Helps identify which model is processing requests
- **Usage**: Useful for logging, debugging, and model-specific behavior

**Possible Return Values**:
- `"MS_Azure_OpenAI_4"`: Indicates GPT-4 model is configured
- Model name reflects the internal identifier used by the extension

## Validation Rules

| Validation | Error Message | Resolution |
|------------|---------------|------------|
| Configuration Check | "AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration." | Complete extension configuration by selecting a model |
| API Key Missing | "Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource." | Configure extension with valid Azure OpenAI API key |

## Error Handling

### Input Errors (INPUT_ERROR)
**Cause**: Not applicable (no input parameters)  
**Resolution**: N/A

### Logic Errors (LOGIC_ERROR)
**Cause**: Extension configuration issues
**Error Messages**:
- "AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration."
- "Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource."

**Common Scenarios**:
- Extension configuration incomplete
- Model not selected from dropdown
- API key not provided
- Invalid configuration state

**Resolution**: Complete the extension configuration by providing all required parameters and selecting a model from the dropdown menu.

### System Errors (SYSTEM_ERROR)
**Cause**: System-level issues accessing configuration  
**Common Scenarios**:
- Configuration service unavailable
- Database connectivity issues
- Internal system errors

**Resolution**: Check system status and retry request

## Usage Examples

### Example 1: Basic Model Information Request

**Input**:
```
No input parameters required
```

**Output**:
```
MS_Azure_OpenAI_4
```

**Result**: Returns the identifier for the configured GPT-4 model.

### Example 2: Configuration Verification

**Input**:
```
No input parameters required
```

**Output**:
```
MS_Azure_OpenAI_4
```

**Result**: Confirms that GPT-4 is the active model for processing requests.

## Business Rules

1. **Configuration Dependency**: Returns information based on current extension configuration
2. **Real-time Information**: Reflects the currently active model configuration
3. **No Side Effects**: Read-only operation that doesn't modify any state
4. **Immediate Response**: Returns cached configuration information quickly
5. **Consistency**: Always returns the same value until configuration changes

## Limitations

1. **Configuration Dependent**: Requires properly configured extension
2. **Static Information**: Returns configuration data, not dynamic model status
3. **No Model Validation**: Doesn't verify if the model is actually accessible
4. **Internal Identifier**: Returns internal model identifier, not Azure deployment name

## Best Practices

### 1. Use for Logging
Include model information in logs to track which model processed specific requests.

### 2. Configuration Verification
Use this request to verify extension configuration before processing complex queries.

### 3. Debugging Support
Include model information when reporting issues or debugging problems.

### 4. Monitoring Integration
Incorporate model information into monitoring and analytics systems.

## Common Use Cases

### 1. System Health Checks
```
Scenario: Verify extension is properly configured
Action: Call Get AI Model Name
Result: Receive model identifier confirming configuration
```

### 2. Request Logging
```
Scenario: Log which model processed a request
Action: Get model name before processing queries
Result: Include model information in audit logs
```

### 3. Configuration Validation
```
Scenario: Validate extension setup during deployment
Action: Call Get AI Model Name after configuration
Result: Confirm expected model is configured
```

### 4. Troubleshooting Support
```
Scenario: User reports unexpected AI behavior
Action: Check configured model name
Result: Verify correct model is being used
```

## Related Catalog Requests

- [Ask LLM](/pages/AskLLM.md) - Query the language model identified by this request

## Technical Implementation

### Helper Class
- **Class**: QueryImpl
- **Package**: app.krista.extensions.impl
- **Validation**: Configuration state validation
- **Service**: Returns hardcoded model identifier based on configuration

### Method Details
The implementation returns a static string identifier:
```java
public String getAIModel() {
    return "MS_Azure_OpenAI_4";
}
```

### Telemetry Metrics
- **REQUEST_COUNT**: Number of model name requests
- **RESPONSE_TIME**: Time taken to retrieve model information
- **ERROR_COUNT**: Number of failed requests

## Troubleshooting

### Configuration Not Found
**Error**: "AI Model is not selected. Please choose either 'ChatGPT 3.5' or 'ChatGPT 4' in the extension configuration."
**Cause**: Extension configuration incomplete
**Solution**:
1. Navigate to extension configuration
2. Select either "ChatGPT 3.5" or "ChatGPT 4" from the Model Name dropdown
3. Ensure API Key and Service URL are also configured
4. Save the configuration and retry

### API Key Missing
**Error**: "Azure OpenAI API Key is not configured. Please configure the extension with a valid API key from your Azure OpenAI resource."
**Cause**: API key not provided in configuration
**Solution**:
1. Obtain API key from Azure portal (Keys and Endpoint section)
2. Configure the extension with the valid API key
3. Complete all other required configuration parameters
4. Test the connection and retry

### System Errors
**Cause**: Internal system issues accessing configuration
**Solution**:
1. Check system status and logs
2. Verify extension is properly installed
3. Retry the request after a brief wait
4. Contact support if the issue persists

## See Also

- [Extension Configuration](/pages/ExtensionConfiguration.md) - Configure the extension and model selection
- [Ask LLM](/pages/AskLLM.md) - Use the configured model to process queries
- [Authentication](/pages/Authentication.md) - Understand authentication requirements
