package com.example.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MultipleChoice {
    private String correct;
    private List<String> answers = new ArrayList<>();

    public MultipleChoice(String correct, String... answers) {
        this.correct = correct;
        this.answers.addAll(Arrays.asList(answers));
    }

    public MultipleChoice() {
    }

    public Optional<String> correctAnswer(){
        return Optional.of(this.correct);
    }

    public Optional<List<String>> answers(){
        return Optional.of(this.answers);
    }
}
