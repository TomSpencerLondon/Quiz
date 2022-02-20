package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;

import java.util.List;

public class TestQuizFactory {

    public static Quiz createQuizWithSingleChoiceQuestions(int count) {
        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        List<Choice> choices = List.of(
                new Choice("Answer 1")
        );
        SingleChoice choice = new SingleChoice(new Choice("Answer 1"), choices);

        for (int i = 1; i <= count; i++) {
            questionRepository.save(new Question("Question " + i, choice));
        }

        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }
}
