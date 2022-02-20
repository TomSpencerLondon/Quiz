package com.example.quiz.adapter.in.web;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.SingleChoice;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

@TestConfiguration
class TestQuizConfiguration {

    @Primary
    @Bean
    Quiz createTestQuiz() {
        final Question question = new Question(
                "Question 1",
                new SingleChoice(new Choice("Answer 1"),
                        List.of(new Choice("Answer 1"), new Choice("Answer 2"))));
        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);
        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }
}
