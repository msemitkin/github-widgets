package com.github.msemitkin.githubstreakwidget;

import java.util.List;

public class ContributionCalendar {
    private Integer totalContributions;
    private List<Week> weeks;

    public Integer getTotalContributions() {
        return totalContributions;
    }

    public void setTotalContributions(Integer totalContributions) {
        this.totalContributions = totalContributions;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
