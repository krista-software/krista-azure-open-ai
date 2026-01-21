# Creating Azure OpenAI App

## Overview

This guide walks you through creating an Azure OpenAI resource in Microsoft Azure, which is required to use the MS Azure OpenAI Extension with Krista. You'll learn how to set up the service, obtain API keys, and configure endpoints.

## Prerequisites

- **Azure Account**: Active Microsoft Azure subscription
- **Permissions**: Contributor or Owner role in your Azure subscription
- **Access Request**: Azure OpenAI service access (may require approval)

> **📝 Note**: Azure OpenAI is currently available by application only. You may need to request access through the Azure portal.

## Step-by-Step Instructions

### 1. Access Azure Portal

Navigate to the [Azure Portal](https://portal.azure.com) and sign in with your Azure account credentials.

![Azure Portal Login](../_media/CreatingAzureOpenAIApp_azure_portal_login.png)

### 2. Create Azure OpenAI Resource

1. In the Azure portal, click **"Create a resource"** or use the **"+"** button
2. Search for **"Azure OpenAI"** in the marketplace
3. Select **"Azure OpenAI"** from the search results
4. Click **"Create"**

![Create Azure OpenAI Resource](../_media/CreatingAzureOpenAIApp_create_resource.png)

### 3. Configure Basic Settings

Fill in the required information for your Azure OpenAI resource:

- **Subscription**: Select your Azure subscription
- **Resource Group**: Choose existing or create new resource group
- **Region**: Select a supported region (e.g., East US, West Europe)
- **Name**: Enter a unique name for your Azure OpenAI resource
- **Pricing Tier**: Select appropriate pricing tier (Standard S0 recommended)

![Basic Configuration](../_media/CreatingAzureOpenAIApp_basic_config.png)

> **⚠️ Warning**: Choose your region carefully as it affects model availability and latency.

### 4. Configure Network Settings

1. **Public Access**: Choose "All networks" for initial setup
2. **Private Endpoints**: Configure if required by your organization
3. **Managed Identity**: Enable if needed for advanced scenarios

![Network Configuration](../_media/CreatingAzureOpenAIApp_network_config.png)

### 5. Review and Create

1. Review all configuration settings
2. Click **"Review + create"**
3. Validate the configuration
4. Click **"Create"** to deploy the resource

![Review and Create](../_media/CreatingAzureOpenAIApp_review_create.png)

Deployment typically takes 2-5 minutes to complete.

### 6. Deploy Models

After the resource is created:

1. Navigate to your Azure OpenAI resource
2. Go to **"Model deployments"** in the left menu
3. Click **"Create new deployment"**
4. Select your desired model:
   - **GPT-3.5 Turbo**: For general-purpose tasks
   - **GPT-4**: For advanced reasoning tasks
5. Configure deployment settings:
   - **Deployment name**: Choose a descriptive name
   - **Model version**: Select the latest stable version
   - **Deployment type**: Standard
6. Click **"Create"**

![Model Deployment](../_media/CreatingAzureOpenAIApp_model_deployment.png)

> **💡 Tip**: You can deploy multiple models to the same resource for different use cases.

### 7. Obtain API Keys

1. In your Azure OpenAI resource, navigate to **"Keys and Endpoint"**
2. Copy one of the API keys (Key 1 or Key 2)
3. Note the **Endpoint URL** - you'll need both for configuration

![API Keys and Endpoint](../_media/CreatingAzureOpenAIApp_keys_endpoint.png)

> **🔒 Security**: Store API keys securely and never share them publicly.

### 8. Configure Access Policies (Optional)

For enhanced security:

1. Go to **"Access control (IAM)"**
2. Add role assignments as needed
3. Configure **"Networking"** settings for IP restrictions
4. Set up **"Private endpoints"** if required

## Verification

To verify your setup is correct:

1. **Resource Status**: Ensure the resource shows "Running" status
2. **Model Deployment**: Confirm your model deployment is "Succeeded"
3. **API Access**: Test API connectivity using Azure's built-in playground
4. **Endpoint Format**: Your endpoint should look like: `https://your-resource-name.openai.azure.com/`

## Next Steps

Once your Azure OpenAI resource is ready:

1. **Configure Extension**: Use the API key and endpoint in the [Extension Configuration](/pages/ExtensionConfiguration.md)
2. **Set Up Authentication**: Review [Authentication](/pages/Authentication.md) best practices
3. **Test Connection**: Validate your setup using Krista's connection test feature

## Troubleshooting

### Common Issues

**Issue**: "Azure OpenAI is not available in your region"  
**Solution**: Try a different Azure region or request access for your preferred region

**Issue**: "Access denied to Azure OpenAI"  
**Solution**: Submit an access request through the Azure portal and wait for approval

**Issue**: "Model deployment failed"  
**Solution**: Check quota limits and try deploying in a different region

**Issue**: "Invalid endpoint URL"  
**Solution**: Ensure you're using the correct format: `https://your-resource-name.openai.azure.com/`

### Getting Help

- **Azure Support**: Use Azure portal support for resource-related issues
- **Documentation**: Visit [Azure OpenAI documentation](https://docs.microsoft.com/azure/cognitive-services/openai/)
- **Krista Support**: Contact [support@krista.ai](mailto:support@krista.ai) for extension-specific issues

## See Also

- [Extension Configuration](/pages/ExtensionConfiguration.md) - Configure the extension with your Azure credentials
- [Authentication](/pages/Authentication.md) - Understand Azure OpenAI authentication
- [Ask LLM](/pages/AskLLM.md) - Start making queries to your deployed models
