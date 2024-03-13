package com.github.msemitkin.githubstreakwidget;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContributionServiceTest {

    @Mock
    private ContributionSource contributionSource;

    @InjectMocks
    private ContributionService contributionService;

    @Test
    void testGetTotalContributionsWithEmptyCalendar() {
        String username = "testUser";

        when(contributionSource.getContributionCalendar(username)).thenReturn(Mono.empty());

        StepVerifier.create(contributionService.getTotalContributions(username))
            .verifyComplete();
    }

    @Test
    void testGetTotalContributions() {
        String username = "testUser";
        ContributionCalendar contributionCalendar = new ContributionCalendar();
        contributionCalendar.setTotalContributions(100);

        when(contributionSource.getContributionCalendar(username)).thenReturn(Mono.just(contributionCalendar));

        StepVerifier.create(contributionService.getTotalContributions(username))
            .expectNext(100)
            .verifyComplete();
    }

    @Test
    void testGetCurrentStreakWithNoContributions() {
        String username = "testUser";
        ContributionCalendar contributionCalendar = new ContributionCalendar();
        contributionCalendar.setWeeks(List.of(
            new Week(List.of(
                new ContributionDay(1, "2022-01-01"),
                new ContributionDay(1, "2022-01-02"),
                new ContributionDay(1, "2022-01-03"),
                new ContributionDay(0, "2022-01-04")
            ))
        ));

        when(contributionSource.getContributionCalendar(username)).thenReturn(Mono.just(contributionCalendar));

        StepVerifier.create(contributionService.getCurrentStreak(username))
            .expectNext(3)
            .verifyComplete();
    }

    @Test
    void testGetCurrentStreak() {
        String username = "testUser";
        ContributionCalendar contributionCalendar = new ContributionCalendar();
        contributionCalendar.setWeeks(List.of(
            new Week(List.of(
                new ContributionDay(0, "2022-01-01"),
                new ContributionDay(1, "2022-01-02"),
                new ContributionDay(1, "2022-01-03"),
                new ContributionDay(1, "2022-01-04"),
                new ContributionDay(1, "2022-01-05")
            ))
        ));

        when(contributionSource.getContributionCalendar(username))
            .thenReturn(Mono.just(contributionCalendar));

        StepVerifier.create(contributionService.getCurrentStreak(username))
            .expectNext(4)
            .verifyComplete();
    }

    @Test
    void testGetLongestStreak() {
        String username = "testUser";
        ContributionCalendar contributionCalendar = new ContributionCalendar();
        contributionCalendar.setWeeks(List.of(
            new Week(List.of(
                new ContributionDay(1, "2022-01-01"),
                new ContributionDay(1, "2022-01-02"),
                new ContributionDay(0, "2022-01-03"),
                new ContributionDay(1, "2022-01-04"),
                new ContributionDay(1, "2022-01-05")
            ))
        ));

        when(contributionSource.getContributionCalendar(username)).thenReturn(Mono.just(contributionCalendar));

        StepVerifier.create(contributionService.getLongestStreak(username))
            .expectNext(2)
            .verifyComplete();
    }


}
