package com.example.lostpetfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/lost-pet")
    public String lostPetPage() {
        return "lost-pet";
    }

    @GetMapping("/found-animal")
    public String foundAnimalPage() {
        return "found-animal";
    }

    @GetMapping("/matches")
    public String matchesPage() {
        return "matches";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }
}