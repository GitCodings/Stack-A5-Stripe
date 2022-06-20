package com.gitcodings.stack.stripe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "stripe")
public class StripeServiceConfig
{
    private final String apiKey;

    public StripeServiceConfig(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getApiKey()
    {
        return apiKey;
    }
}
