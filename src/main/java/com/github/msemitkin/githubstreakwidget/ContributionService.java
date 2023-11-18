package com.github.msemitkin.githubstreakwidget;

import org.springframework.stereotype.Service;
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

    public int getCurrentStreak(String username) {
        ContributionCalendar contributionCalendar = contributionSource.getContributionCalendar(username)
            .blockOptional()
            .orElseThrow();
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
        ContributionCalendar contributionCalendar = contributionSource.getContributionCalendar(username)
            .blockOptional()
            .orElseThrow();
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

}
