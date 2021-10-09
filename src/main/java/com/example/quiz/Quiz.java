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

    public Question mark(Question question, Answer answer) {
        int index = questions.indexOf(question);
        final Question markedQuestion = question.mark(answer);
        questions.set(index, markedQuestion);
        return markedQuestion;
    }
}
