package com.example.quiz.domain.factories;

import com.example.quiz.domain.*;

import java.util.ArrayList;
import java.util.List;

public class QuizTestFactory {

    @Deprecated
    public static Quiz createQuizWithSingleChoiceQuestions(int count) {
        List<Choice> choices = List.of(
                new Choice("Answer 1", true)
        );
        ChoiceType choice = new SingleChoice(choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId(QuestionId.of((long) i));
            questions.add(q);
        }
        return new Quiz(questions);
    }

    @Deprecated
    public static Quiz createQuizWithSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false))));
        List<Question> questions = List.of(question);
        Quiz quiz = new Quiz(questions);
        return quiz;
    }
}
