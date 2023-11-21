package com.github.msemitkin.githubstreakwidget;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public HttpGraphQlClient httpGraphQlClient(
        WebClient webClient,
        @Value("${github.token}") String githubToken
    ) {
        return HttpGraphQlClient.builder(webClient)
            .header("Authorization", "Bearer " + githubToken)
            .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("https://api.github.com/graphql");
    }
}
