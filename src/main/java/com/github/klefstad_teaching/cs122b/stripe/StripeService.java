package com.github.klefstad_teaching.cs122b.stripe;

import com.github.klefstad_teaching.cs122b.stripe.config.StripeServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    StripeServiceConfig.class
})
public class StripeService
{
    public static void main(String[] args)
    {
        SpringApplication.run(StripeService.class, args);
    }
}
