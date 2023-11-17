package com.github.msemitkin.githubstreakwidget;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StreakController {
    private final ContributionSource contributionSource;
    private final ImageService imageService;

    public StreakController(ContributionSource contributionSource, ImageService imageService) {
        this.contributionSource = contributionSource;
        this.imageService = imageService;
    }

    @GetMapping("/contributions/{username}")
    public Mono<Integer> getTotalContributions(@PathVariable String username) {
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

    @GetMapping(value = "/total-contributions/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getTotalContributionsImage(@PathVariable String username) {
        int totalContributions = contributionSource.getTotalContributions(username).blockOptional().orElseThrow();
        return imageService.createImage("👾 Total contributions: " + totalContributions);
    }

    @GetMapping(value = "/current-streak/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCurrentStreakImage(@PathVariable String username) {
        int currentStreak = contributionSource.getCurrentStreak(username);
        return imageService.createImage("🔥 Current streak: " + currentStreak);
    }

    @GetMapping(value = "/longest-streak/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getLongestStreakImage(@PathVariable String username) {
        int currentStreak = contributionSource.getLongestStreak(username);
        return imageService.createImage("🔥 Longest streak: " + currentStreak);
    }

}
