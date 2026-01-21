# Release Notes for MS Azure OpenAI Extension

## Version 1.0.1


- **Developer**: Ramgopal Patidar
- **Krista API Version**: 1.0.117
- **Global Catalog Version**: GC-2025.11.1

### New Features
- Upgrade Extensions to Java 21 and Standardize Error Messages, Documentation, Jenkins Build, and Unit Test Cases

## Version 1.0.0

- **Developer**: Pranav Kshirsagar
- **Krista API Version**: 1.0.92
- **Global Catalog Version**: 1.0.43
- **Appliance Release Version**: 3.2.7

### Catalog Requests

#### Ask LLM
- **Type**: QUERY_SYSTEM
- **Description**: Query Azure OpenAI language models with JSON-formatted prompts
- **Input**: JSON string containing messages array with conversation history
- **Output**: Generated response from the configured AI model
- **Features**:
  - Multi-turn conversation support
  - System message support for context setting
  - Automatic rate limiting
  - Token management

#### Get AI Model Name
- **Type**: QUERY_SYSTEM
- **Description**: Retrieve the name of the configured AI model
- **Input**: No parameters required
- **Output**: Model identifier string
- **Features**:
  - Configuration verification
  - Debugging support
  - Retry support enabled

### Configuration Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| API Key | Text (Secured) | Yes | Azure OpenAI API key |
| Model Name | Pick One | Yes | GPT-3.5 or GPT-4 selection |
| Service URL | Text | Yes | Azure OpenAI endpoint URL |

### Technical Specifications

- **Authentication**: API key-based with Bearer token
- **Protocol**: HTTPS REST API
- **Rate Limits**: 15 calls/minute, 8,000 tokens/minute
- **Response Timeout**: 60 seconds
- **Connection Timeout**: 10 seconds
- **Max Response Tokens**: 512
- **Context Window**: Model-dependent (4K-32K tokens)

### Enhanced User Experience

- **Improved Error Messages**: All error messages rewritten to be user-friendly and actionable
- **Specific Guidance**: Error messages include exact steps to resolve issues
- **Azure Portal Integration**: Error messages reference specific Azure portal sections
- **Troubleshooting Support**: Comprehensive troubleshooting guidance in documentation
- **Configuration Validation**: Clear validation messages during setup

### Security Features

- **Encrypted Storage**: API keys encrypted at rest
- **HTTPS Only**: All communications over encrypted connections
- **Access Control**: Role-based access to configuration
- **Audit Logging**: Configuration changes logged
- **Key Rotation**: Support for API key rotation

### Dependencies

- **Azure OpenAI Service**: Requires active Azure OpenAI resource
- **Network Access**: Outbound HTTPS access to Azure endpoints
- **Krista Platform**: Compatible with Krista API version 1.0.92+

### Resolved Bugs

No bugs resolved in initial release.

### Known Issues

#### Rate Limiting
- **Issue**: Azure OpenAI rate limits may cause temporary request failures
- **Workaround**: Built-in rate limiting helps manage this automatically
- **Status**: By design - Azure service limitation

#### Model Availability
- **Issue**: Model availability depends on Azure region and deployment
- **Workaround**: Ensure models are properly deployed in your Azure resource
- **Status**: Configuration dependent

#### Token Limits
- **Issue**: Large conversations may exceed token limits
- **Workaround**: Manage conversation length and context
- **Status**: Model limitation

### Upgrade Notes

This is the initial release of the MS Azure OpenAI Extension.

### Support

For technical support:
- **Email**: [support@krista.ai](mailto:support@krista.ai)
- **Documentation**: See extension documentation for detailed setup and usage
- **Azure Support**: Use Azure portal for Azure OpenAI service issues

### See Also

- [Extension Configuration](/pages/ExtensionConfiguration.md) - Setup and configuration guide
- [Creating Azure OpenAI App](/pages/CreatingAzureOpenAIApp.md) - Azure resource setup
- [Authentication](/pages/Authentication.md) - Security and authentication details
- [Ask LLM](/pages/AskLLM.md) - Primary catalog request documentation

