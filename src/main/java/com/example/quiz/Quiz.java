package com.example.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public class Quiz {

    private List<String> questions = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(String... questions) {
        this.questions.add(questions[0]);
    }


    public List<String> questions() {
        return questions;
    }
}
