package com.github.msemitkin.githubstreakwidget;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
        // stub
    }
}
