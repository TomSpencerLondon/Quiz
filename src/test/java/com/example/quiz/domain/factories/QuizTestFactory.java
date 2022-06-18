package com.example.quiz.domain.factories;

import com.example.quiz.hexagon.domain.*;

import java.util.List;

public class QuizTestFactory {

    @Deprecated
    public static Quiz createQuizWithSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false))));
        List<QuestionId> questionIds = List.of(question).stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        return quiz;
    }
}
