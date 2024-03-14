package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.external.ExternalApiParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    private final ExternalApiParser parser;

    public TestController(ExternalApiParser parser) {
        this.parser = parser;
    }

    @GetMapping("/api/test")
    public String test() {
        parser.parserAndSave();
        return "redirect:/articles";
    }
}
