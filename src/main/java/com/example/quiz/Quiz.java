package com.example.quiz;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Quiz {

    private List<Question> questions = new ArrayList<>();

    public Quiz(Question... questions) {
        this.questions.addAll(asList(questions));
    }

    public List<Question> questions() {
        return questions;
    }
}
