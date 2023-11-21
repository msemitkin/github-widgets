package com.github.msemitkin.githubstreakwidget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Timed;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
public class ContributionSource {
    private static final Logger logger = LoggerFactory.getLogger(ContributionSource.class);

    private final HttpGraphQlClient httpGraphQlClient;

    public ContributionSource(HttpGraphQlClient httpGraphQlClient) {
        this.httpGraphQlClient = httpGraphQlClient;
    }

    public Mono<ContributionCalendar> getContributionCalendar(String username) {
        return httpGraphQlClient
            .document("""
                query($userName:String!) {
                  user(login: $userName){
                    contributionsCollection {
                      contributionCalendar {
                        totalContributions
                        weeks {
                          contributionDays {
                            contributionCount
                            date
                          }
                        }
                      }
                    }
                  }
                }
                """
            )
            .variable("userName", username)
            .retrieve("user.contributionsCollection.contributionCalendar")
            .toEntity(ContributionCalendar.class)
            .timed()
            .doOnNext(it -> logger.info("ContributionSource.getContributionCalendar: {}", it.elapsed()))
            .timeout(Duration.ofSeconds(3))
            .onErrorMap(TimeoutException.class, e -> new GitHubIntegrationException("GitHub server request timeout", e))
            .map(Timed::get);
    }
}
