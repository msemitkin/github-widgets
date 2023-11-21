package com.github.msemitkin.githubstreakwidget;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/total-contributions/{username}", produces = "image/svg+xml")
    public Mono<ResponseEntity<byte[]>> getTotalContributionsImage(@PathVariable String username) {
        return contributionService.getTotalContributions(username)
            .map(totalContributions -> imageService.createSvg(totalContributions, "Total contributions"))
            .map(image -> ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(image));
    }

    @GetMapping(value = "/current-streak/{username}", produces = "image/svg+xml")
    public Mono<ResponseEntity<byte[]>> getCurrentStreakImage(@PathVariable String username) {
        return contributionService.getCurrentStreak(username)
            .map(streak -> imageService.createSvg(streak, "Current streak"))
            .map(image -> ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(image));
    }

    @GetMapping(value = "/longest-streak/{username}", produces = "image/svg+xml")
    public Mono<ResponseEntity<byte[]>> getLongestStreakImage(@PathVariable String username) {
        return contributionService.getLongestStreak(username)
            .map(streak -> imageService.createSvg(streak, "Longest streak"))
            .map(image -> ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(image));
    }

}
