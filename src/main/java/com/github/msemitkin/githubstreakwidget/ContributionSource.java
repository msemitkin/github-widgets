package com.github.msemitkin.githubstreakwidget;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ContributionSource {
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
            .toEntity(ContributionCalendar.class);
    }
}
