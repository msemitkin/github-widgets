package com.github.msemitkin.githubstreakwidget;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreakController {
    private final ContributionSource contributionSource;

    public StreakController(ContributionSource contributionSource) {
        this.contributionSource = contributionSource;
    }

    @GetMapping("/contributions/{username}")
    public int getTotalContributions(@PathVariable String username) {
        return contributionSource.getTotalContributions(username);
    }

    @GetMapping("/streak/{username}")
    public int getStreak(@PathVariable String username, @RequestParam String type) {
        if (type.equals("longest")) {
            return contributionSource.getLongestStreak(username);
        } else if (type.equals("current")) {
            return contributionSource.getCurrentStreak(username);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }

}
