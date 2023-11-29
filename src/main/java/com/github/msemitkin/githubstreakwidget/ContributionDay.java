package com.github.msemitkin.githubstreakwidget;


public class ContributionDay {
    private Integer contributionCount;
    private String date;

    public ContributionDay() {
    }

    public ContributionDay(Integer contributionCount, String date) {
        this.contributionCount = contributionCount;
        this.date = date;
    }

    public Integer getContributionCount() {
        return contributionCount;
    }

    public void setContributionCount(Integer contributionCount) {
        this.contributionCount = contributionCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
