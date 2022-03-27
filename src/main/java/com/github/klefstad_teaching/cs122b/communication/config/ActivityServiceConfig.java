package com.github.klefstad_teaching.cs122b.communication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "activity")
public class ActivityServiceConfig
{
    private final String stripeApiKey;

    public ActivityServiceConfig(String stripeApiKey)
    {
        this.stripeApiKey = stripeApiKey;
    }

    public String getStripeApiKey()
    {
        return stripeApiKey;
    }
}
