package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.QuestionBuilder;
import com.example.quiz.domain.QuizBuilder;
import com.example.quiz.hexagon.application.QuizService;
import com.example.quiz.hexagon.application.QuizSessionService;
import com.example.quiz.hexagon.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.application.port.QuizRepository;
import com.example.quiz.hexagon.application.port.TokenGenerator;
import com.example.quiz.hexagon.domain.Question;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
class TestSessionConfiguration {
    @Primary
    @Bean
    QuizService createTestQuizService() {
        return new QuizService(new StubQuestionRepository());
    }

    @Primary
    @Bean
    QuizSessionService createTestQuizSessionService() {
        Question question = new QuestionBuilder().withDefaultSingleChoice().build();
        QuizBuilder quizBuilder = new QuizBuilder();
        quizBuilder.withQuestions(question).withId(0L).save();
        QuizRepository quizRepository = quizBuilder.quizRepository();
        return new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());
    }

    @Primary
    @Bean
    QuestionRepository questionRepository() {
        return new StubQuestionRepository();
    }

    @Primary
    @Bean
    TokenGenerator createIdGenerator() {
        return new StubTokenGenerator();
    }
}
