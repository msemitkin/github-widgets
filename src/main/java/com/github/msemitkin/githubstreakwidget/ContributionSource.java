package com.github.msemitkin.githubstreakwidget;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class ContributionSource {
    private final HttpGraphQlClient httpGraphQlClient;

    public ContributionSource(HttpGraphQlClient httpGraphQlClient) {
        this.httpGraphQlClient = httpGraphQlClient;
    }

    public Mono<Integer> getTotalContributions(String username) {
        return getContributionCalendar(username)
            .map(ContributionCalendar::getTotalContributions);
    }

    public int getCurrentStreak(String username) {
        ContributionCalendar contributionCalendar = getContributionCalendar(username).blockOptional().orElseThrow();
        List<Week> weeks = contributionCalendar.getWeeks();
        Collections.reverse(weeks);
        int currentStreak = 0;
        for (Week week : weeks) {
            List<ContributionDay> contributionDays = week.getContributionDays();
            Collections.reverse(contributionDays);
            for (ContributionDay contributionDay : contributionDays) {
                if (contributionDay.getContributionCount() > 0) {
                    currentStreak++;
                } else {
                    return currentStreak;
                }
            }
        }
        return currentStreak;
    }

    public int getLongestStreak(String username) {
        ContributionCalendar contributionCalendar = getContributionCalendar(username).blockOptional().orElseThrow();
        int longestStreak = 0;
        int currentStreak = 0;
        for (Week week : contributionCalendar.getWeeks()) {
            for (ContributionDay contributionDay : week.getContributionDays()) {
                if (contributionDay.getContributionCount() > 0) {
                    currentStreak++;
                    if (currentStreak > longestStreak) {
                        longestStreak = currentStreak;
                    }
                } else {
                    currentStreak = 0;
                }
            }
        }
        return longestStreak;
    }


    private Mono<ContributionCalendar> getContributionCalendar(String username) {
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
