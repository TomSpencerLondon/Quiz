package com.example.quiz.domain;

import java.util.ArrayList;
import java.util.List;

public class TestQuizFactory {

    public static Quiz createQuizWithSingleChoiceQuestions(int count) {
        List<Choice> choices = List.of(
                new Choice("Answer 1")
        );
        ChoiceType choice = new SingleChoice(new Choice("Answer 1"), choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId((long) i);
            questions.add(q);
        }
        return new Quiz(questions);
    }
}
