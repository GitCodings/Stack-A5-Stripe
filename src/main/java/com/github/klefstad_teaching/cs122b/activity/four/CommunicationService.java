package com.github.klefstad_teaching.cs122b.activity.four;

import com.github.klefstad_teaching.cs122b.activity.four.config.ActivityServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    ActivityServiceConfig.class
})
public class CommunicationService
{
    public static void main(String[] args)
    {
        SpringApplication.run(CommunicationService.class, args);
    }
}
