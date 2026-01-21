# Authentication

## Overview

The MS Azure OpenAI Extension uses API key-based authentication to securely connect to your Azure OpenAI service. This document explains the authentication mechanism, API key management, and security best practices for maintaining secure access to your Azure OpenAI resources.

## Authentication Modes

### API Key Authentication

The Azure OpenAI extension uses **API Key Authentication** as the primary authentication method:

- **Method**: Bearer token authentication using Azure-generated API keys
- **Protocol**: HTTPS with API key in request headers
- **Security**: Keys are encrypted and stored securely within Krista
- **Scope**: Full access to all deployed models within the Azure OpenAI resource

#### When to Use API Key Authentication
- **Standard Use Cases**: Most common authentication method for Azure OpenAI
- **Simplicity**: Easy to set up and manage
- **Direct Access**: Direct connection to Azure OpenAI service
- **Development**: Ideal for development and testing environments

## API Key Management

### Obtaining API Keys

Azure OpenAI provides two API keys for each resource:

1. **Primary Key (Key 1)**: Main API key for production use
2. **Secondary Key (Key 2)**: Backup key for rotation scenarios

#### Key Characteristics
- **Format**: 32-character alphanumeric string
- **Validity**: Keys remain valid until manually regenerated
- **Permissions**: Both keys provide identical access levels
- **Usage**: Either key can be used interchangeably

### Key Rotation Process

Regular key rotation is a security best practice:

1. **Preparation**: Ensure you have both primary and secondary keys
2. **Switch to Secondary**: Update extension configuration to use Key 2
3. **Regenerate Primary**: Generate new Key 1 in Azure portal
4. **Test**: Validate connection with new primary key
5. **Update Configuration**: Switch back to new primary key
6. **Regenerate Secondary**: Generate new Key 2 for future rotation

> **💡 Tip**: Plan key rotation during maintenance windows to avoid service interruption.

### Key Storage and Security

#### In Krista
- **Encryption**: API keys are encrypted at rest
- **Access Control**: Only authorized users can view/modify keys
- **Audit Trail**: Configuration changes are logged
- **Secure Transmission**: Keys transmitted over encrypted connections

#### Best Practices
- **Never Log Keys**: Avoid logging API keys in application logs
- **Environment Variables**: Use secure environment variables when possible
- **Access Reviews**: Regularly review who has access to API keys
- **Monitoring**: Monitor API usage for unusual patterns

## Authentication Flow

### Request Authentication

Each request to Azure OpenAI follows this authentication pattern:

1. **Header Addition**: API key added to request header
2. **HTTPS Transmission**: Request sent over encrypted connection
3. **Azure Validation**: Azure validates the API key
4. **Resource Access**: Access granted to deployed models
5. **Response**: Azure returns requested data

#### Request Format
```http
POST https://your-resource.openai.azure.com/openai/deployments/your-model/chat/completions?api-version=2023-05-15
Authorization: Bearer YOUR_API_KEY
Content-Type: application/json
```

### Connection Validation

The extension validates authentication during setup:

1. **Test Request**: Sends a simple test message to Azure OpenAI
2. **Authentication Check**: Verifies API key validity
3. **Model Access**: Confirms access to selected model
4. **Endpoint Validation**: Validates service URL accessibility
5. **Success Confirmation**: Reports successful authentication

## Security Best Practices

### API Key Security

#### Do's ✅
- **Secure Storage**: Store keys in encrypted configuration
- **Regular Rotation**: Rotate keys every 90 days
- **Principle of Least Privilege**: Grant minimum required access
- **Monitor Usage**: Track API usage patterns
- **Use HTTPS**: Always use encrypted connections
- **Separate Environments**: Use different keys for dev/test/prod

#### Don'ts ❌
- **Never Hardcode**: Don't embed keys in source code
- **Avoid Plain Text**: Don't store keys in plain text files
- **No Public Repositories**: Never commit keys to version control
- **Don't Share**: Avoid sharing keys via email or chat
- **No Client-Side**: Never expose keys in client-side code
- **Avoid Logging**: Don't log API keys

### Network Security

#### Azure-Side Configuration
- **IP Restrictions**: Configure IP allowlists in Azure portal
- **Private Endpoints**: Use Azure private endpoints for enhanced security
- **Virtual Networks**: Deploy within Azure virtual networks
- **Firewall Rules**: Configure Azure firewall rules

#### Krista-Side Configuration
- **Outbound Rules**: Ensure firewall allows HTTPS to Azure
- **DNS Resolution**: Verify DNS can resolve Azure endpoints
- **Certificate Validation**: Enable SSL certificate validation
- **Proxy Configuration**: Configure proxy settings if required

### Compliance and Governance

#### Audit and Monitoring
- **Access Logs**: Enable Azure audit logging
- **Usage Monitoring**: Monitor API call patterns
- **Cost Tracking**: Track usage costs and quotas
- **Security Alerts**: Set up alerts for unusual activity

#### Compliance Requirements
- **Data Residency**: Understand where data is processed
- **Retention Policies**: Configure data retention settings
- **Privacy Controls**: Implement privacy protection measures
- **Regulatory Compliance**: Ensure compliance with applicable regulations

## Troubleshooting Authentication

### Common Authentication Issues

#### Invalid API Key
**Error**: "Azure OpenAI API Key is required. Please provide a valid API key from your Azure OpenAI resource. You can find this in the Azure portal under 'Keys and Endpoint'."
**Causes**:
- API key not configured in extension
- Incorrect API key format
- Key has been regenerated in Azure portal
- Copy/paste errors with extra characters

**Resolution**:
1. Log into Azure portal and navigate to your Azure OpenAI resource
2. Go to "Keys and Endpoint" section
3. Copy either Key 1 or Key 2 exactly as displayed
4. Update extension configuration with the correct key
5. Ensure no extra spaces or characters are included
6. Test the connection after updating

#### Access Denied
**Error**: "403 Forbidden" or "Access denied"  
**Causes**:
- API key lacks required permissions
- Resource-level access restrictions
- IP address not in allowlist

**Resolution**:
1. Check Azure resource permissions
2. Verify IP allowlist settings
3. Confirm resource is properly configured
4. Review Azure access policies

#### Connection Timeout
**Error**: "Connection timeout" or "Network error"  
**Causes**:
- Firewall blocking outbound connections
- DNS resolution issues
- Azure service outage

**Resolution**:
1. Check firewall rules for HTTPS traffic
2. Verify DNS resolution of Azure endpoint
3. Check Azure service status
4. Test connectivity from different network

#### Model Access Issues
**Error**: "Model not found" or "Deployment not available"  
**Causes**:
- Model not deployed in Azure resource
- Incorrect model name in configuration
- Model deployment still in progress

**Resolution**:
1. Verify model deployment in Azure portal
2. Check model deployment status
3. Confirm model name matches deployment
4. Wait for deployment completion

### Getting Help

#### Azure Support
- **Azure Portal**: Use built-in support for Azure-related issues
- **Documentation**: [Azure OpenAI Documentation](https://docs.microsoft.com/azure/cognitive-services/openai/)
- **Community**: Azure community forums and Stack Overflow

#### Krista Support
- **Email**: [support@krista.ai](mailto:support@krista.ai)
- **Documentation**: This documentation and related guides
- **Support Channel**: Your existing Krista support channel

## See Also

- [Creating Azure OpenAI App](/pages/CreatingAzureOpenAIApp.md) - Set up Azure OpenAI resource
- [Extension Configuration](/pages/ExtensionConfiguration.md) - Configure the extension
- [Ask LLM](/pages/AskLLM.md) - Start making authenticated requests
