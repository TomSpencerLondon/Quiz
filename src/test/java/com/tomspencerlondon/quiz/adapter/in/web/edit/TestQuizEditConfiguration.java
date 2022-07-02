package com.tomspencerlondon.quiz.adapter.in.web.edit;

import com.tomspencerlondon.quiz.hexagon.application.QuizCreator;
import com.tomspencerlondon.quiz.hexagon.application.port.InMemoryQuizRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestQuizEditConfiguration {
    @Primary
    @Bean
    public QuizCreator quizCreator() {
        return new QuizCreator(new InMemoryQuizRepository());
    }
}
