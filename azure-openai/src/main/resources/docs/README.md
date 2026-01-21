# MS Azure OpenAI Extension

## Overview

The MS Azure OpenAI Extension enables Krista to connect to Microsoft Azure's OpenAI service, providing access to powerful language models including GPT-3.5 and GPT-4. This extension serves as a dependency for the AI Q&A extension, allowing you to leverage Azure's enterprise-grade OpenAI implementation for generating intelligent responses in your Krista conversations.

## Key Features

✅ **Enterprise-Grade Security**: Leverage Azure's security and compliance features
✅ **Multiple Model Support**: Access to GPT-3.5 Turbo and GPT-4 models
✅ **Rate Limiting**: Built-in rate limiting to manage API usage
✅ **Connection Validation**: Test connection functionality to verify configuration
✅ **Flexible Configuration**: Support for custom Azure OpenAI endpoints
✅ **Seamless Integration**: Works with Krista's AI Q&A extension

## Quick Start Guide

1. **Set up Azure OpenAI Service**: Create an Azure OpenAI resource in your Azure portal
2. **Configure Extension**: Add the extension to your workspace with API key and endpoint
3. **Validate Connection**: Test the configuration to ensure proper connectivity
4. **Start Using**: Begin making queries through the AI Q&A extension

## Documentation Structure

### Getting Started
- [Extension Configuration](pages/ExtensionConfiguration.md) - Configure the extension with your Azure OpenAI credentials
- [Authentication](pages/Authentication.md) - Understand Azure OpenAI authentication and API key management
- [Creating Azure OpenAI App](pages/CreatingAzureOpenAIApp.md) - Step-by-step Azure OpenAI service setup

### Catalog Requests
- [Ask LLM](pages/AskLLM.md) - Query the configured language model with prompts
- [Get AI Model Name](pages/GetAIModelName.md) - Retrieve the name of the configured AI model

### Additional Resources
- [Release Notes](pages/ReleaseNotes.md) - Version history and updates

## Support & Resources

- **Extension Version**: 1.0.0
- **Krista API Version**: 1.0.92
- **Azure OpenAI API**: Compatible with Azure OpenAI REST API
- **Supported Models**: GPT-3.5 Turbo, GPT-4

## Common Issues & Solutions

### Configuration Errors
If you see configuration-related error messages, they now provide specific guidance:
- **API Key Issues**: Error messages include direct links to Azure portal sections
- **Model Selection**: Clear instructions on which models to choose and how to verify deployment
- **Connection Problems**: Step-by-step troubleshooting with specific checks to perform

### Improved Error Messages
All error messages have been enhanced to be more user-friendly and actionable:
- **Clear Problem Description**: What went wrong and why
- **Specific Resolution Steps**: Exact actions to take to fix the issue
- **Helpful Context**: Where to find required information (e.g., Azure portal sections)

### Quick Troubleshooting
1. **Configuration Issues**: Check extension configuration for missing or invalid parameters
2. **Connection Problems**: Verify Azure OpenAI resource status and network connectivity
3. **Model Errors**: Ensure selected model is deployed in your Azure OpenAI resource
4. **JSON Format Issues**: Validate prompt structure using provided examples

## Need Help?

We always welcome feedback. Please email us at [support@krista.ai](mailto:support@krista.ai) or contact your existing support channel.