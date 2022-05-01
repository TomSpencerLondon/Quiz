package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.application.QuizCreator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestQuizEditConfiguration {
    @Primary
    @Bean
    public QuizCreator quizCreator() {
        return new QuizCreator();
    }
}
