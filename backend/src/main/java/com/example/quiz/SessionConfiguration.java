package com.example.quiz;

import com.example.quiz.adapter.out.web.humanreadable.ReadableTokenGenerator;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SessionConfiguration {
    @Bean
    public QuizService createQuizService(QuestionRepository questionRepository) {
        return new QuizService(questionRepository);
    }

    @Bean
    public QuizSessionService createQuizSessionService() {
        QuizRepository quizRepository = new InMemoryQuizRepository();
        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true))));
        question.setId(QuestionId.of(1L));
        List<QuestionId> questionIds = List.of(question.getId());
        quizRepository.save(new Quiz("Quiz 1", questionIds));
        return new QuizSessionService(
                new InMemoryQuizSessionRepository(),
                quizRepository,
                new ReadableTokenGenerator());
    }

    @Bean
    public TokenGenerator idGenerator() {
        return new ReadableTokenGenerator();
    }
}
