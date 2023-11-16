package com.github.msemitkin.githubstreakwidget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public HttpGraphQlClient httpGraphQlClient(WebClient webClient) {
        return HttpGraphQlClient.builder(webClient)
            .header("Authorization", "Bearer " + System.getenv("GITHUB_TOKEN"))
            .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("https://api.github.com/graphql");
    }
}
