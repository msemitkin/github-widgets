package com.github.msemitkin.githubstreakwidget;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StreakController {
    private static final String DEFAULT_FONT_SIZE = "30";
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

    @GetMapping(value = "/total-contributions/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTotalContributionsImage(
        @PathVariable String username,
        @RequestParam(defaultValue = DEFAULT_FONT_SIZE) Integer fontSize
    ) {
        int totalContributions = contributionService.getTotalContributions(username).blockOptional().orElseThrow();
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createImage("👾 Total contributions: " + totalContributions, fontSize));
    }

    @GetMapping(value = "/current-streak/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getCurrentStreakImage(
        @PathVariable String username,
        @RequestParam(defaultValue = DEFAULT_FONT_SIZE) Integer fontSize
    ) {
        int currentStreak = contributionService.getCurrentStreak(username);
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createImage("🔥 Current streak: " + currentStreak, fontSize));
    }

    @GetMapping(value = "/longest-streak/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getLongestStreakImage(
        @PathVariable String username,
        @RequestParam(defaultValue = DEFAULT_FONT_SIZE) Integer fontSize
    ) {
        int longestStreak = contributionService.getLongestStreak(username);
        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache())
            .body(imageService.createImage("🔥 Longest streak: " + longestStreak, fontSize));
    }

}
