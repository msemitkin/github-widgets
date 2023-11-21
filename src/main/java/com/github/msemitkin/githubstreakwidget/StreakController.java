package com.github.msemitkin.githubstreakwidget;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StreakController {
    private final ContributionService contributionService;
    private final ImageService imageService;

    public StreakController(
        ContributionService contributionService,
        ImageService imageService
    ) {
        this.contributionService = contributionService;
        this.imageService = imageService;
    }

    @GetMapping("/contributions/{username}")
    public Mono<Integer> getTotalContributions(@PathVariable String username) {
        return contributionService.getTotalContributions(username);
    }

    @GetMapping("/streak/{username}")
    public int getStreak(@PathVariable String username, @RequestParam String type) {
        if (type.equals("longest")) {
            return contributionService.getLongestStreak(username);
        } else if (type.equals("current")) {
            return contributionService.getCurrentStreak(username);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }

    @GetMapping(value = "/total-contributions/{username}", produces = "image/svg+xml")
    public ResponseEntity<byte[]> getTotalContributionsImage(@PathVariable String username) {
        int totalContributions = contributionService.getTotalContributions(username).blockOptional().orElseThrow();
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createSvg(totalContributions, "Total contributions"));
    }

    @GetMapping(value = "/current-streak/{username}", produces = "image/svg+xml")
    public ResponseEntity<byte[]> getCurrentStreakImage(@PathVariable String username) {
        int currentStreak = contributionService.getCurrentStreak(username);
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createSvg(currentStreak, "Current streak"));
    }

    @GetMapping(value = "/longest-streak/{username}", produces = "image/svg+xml")
    public ResponseEntity<byte[]> getLongestStreakImage(@PathVariable String username) {
        int longestStreak = contributionService.getLongestStreak(username);
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createSvg(longestStreak, "Longest streak"));
    }

}
