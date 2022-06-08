package com.example.quiz.adapter.in.web.javascript;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JavascriptController {
    @GetMapping("/template")
    public String getTemplate() {
        return "template";
    }

    @GetMapping("/nodes")
    public String getNodes() {
        return "nodes";
    }
}
