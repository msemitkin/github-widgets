package com.github.msemitkin.githubstreakwidget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcessor.class);

    @ExceptionHandler(GitHubIntegrationException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleGitHubIntegrationException(GitHubIntegrationException e) {
        logger.warn("Handled exception: {}", e.getMessage(), e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e) {
        logger.error("Unhandled exception: {}", e.getMessage(), e);
    }
}
