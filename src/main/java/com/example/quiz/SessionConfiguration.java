package com.example.quiz;

import com.example.quiz.adapter.out.web.humanreadable.ReadableIdGenerator;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.IdGenerator;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuestionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration {
    @Bean
    public QuizService createQuizService(QuestionRepository questionRepository) {
        return new QuizService(questionRepository);
    }

    @Bean
    public QuizSessionService createQuizSessionService(QuizService quizService) {
        return new QuizSessionService(quizService, new InMemoryQuizSessionRepository());
    }

    @Bean
    public IdGenerator idGenerator() {
        return new ReadableIdGenerator();
    }
}
