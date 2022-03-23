package com.github.klefstad_teaching.cs122b.activity.four.rest;

import com.github.klefstad_teaching.cs122b.activity.four.model.data.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
public class WebClientController
{
    private final static Logger LOG = LoggerFactory.getLogger(WebClientController.class);

    private final WebClient webClient;
    private final URI       jsonPlaceholder;

    @Autowired
    public WebClientController()
        throws URISyntaxException
    {
        this.webClient =
            WebClient.builder()
                     .build();

        this.jsonPlaceholder = new URI("https://jsonplaceholder.typicode.com/");
    }

    @GetMapping("/webClient")
    public ResponseEntity<?> restCalls()
    {
        Post[] posts = webClient.post()
                                .uri(jsonPlaceholder)
                                .retrieve()
                                .bodyToMono(Post[].class)
                                .block();

        Arrays.stream(posts)
              .forEach(
                  post -> LOG.info(post.getBody()));

        return ResponseEntity.ok().build();
    }
}
