package com.github.msemitkin.githubstreakwidget;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RegisterReflectionForBinding({ContributionCalendar.class, ContributionDay.class, Week.class})
public class GithubStreakWidgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubStreakWidgetApplication.class, args);
    }

}
