package com.github.msemitkin.githubstreakwidget;


import java.util.List;

public class Week {
    private List<ContributionDay> contributionDays;

    public Week() {
    }

    public Week(List<ContributionDay> contributionDays) {
        this.contributionDays = contributionDays;
    }

    public List<ContributionDay> getContributionDays() {
        return contributionDays;
    }

    public void setContributionDays(List<ContributionDay> contributionDays) {
        this.contributionDays = contributionDays;
    }
}
