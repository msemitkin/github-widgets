package com.github.msemitkin.githubstreakwidget;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ContributionService {
    private final ContributionSource contributionSource;

    public ContributionService(ContributionSource contributionSource) {
        this.contributionSource = contributionSource;
    }

    public Mono<Integer> getTotalContributions(String username) {
        return contributionSource.getContributionCalendar(username)
            .map(ContributionCalendar::getTotalContributions);
    }

    public Mono<Integer> getCurrentStreak(String username) {
        return getContributionDays(username)
            .flatMapIterable(it -> {
                Collections.reverse(it);
                return it;
            })
            .takeWhile(contributionDay -> contributionDay.getContributionCount() > 0)
            .count()
            .map(Long::intValue);
    }

    public Mono<Integer> getLongestStreak(String username) {
        return getContributionDays(username)
            .map(contributionDays -> {
                int longestStreak = 0;
                int currentStreak = 0;
                for (ContributionDay contributionDay : contributionDays) {
                    if (contributionDay.getContributionCount() > 0) {
                        currentStreak++;
                        longestStreak = Math.max(longestStreak, currentStreak);
                    } else {
                        currentStreak = 0;
                    }
                }
                return longestStreak;
            });
    }

    private Mono<List<ContributionDay>> getContributionDays(String username) {
        return contributionSource.getContributionCalendar(username)
            .flatMapMany(contributionCalendar -> Flux.fromIterable(contributionCalendar.getWeeks()))
            .flatMap(week -> Flux.fromIterable(week.getContributionDays()))
            .collectList();
    }

}
