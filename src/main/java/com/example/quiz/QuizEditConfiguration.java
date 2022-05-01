package com.example.quiz;

import com.example.quiz.application.QuizCreator;
import com.example.quiz.application.port.InMemoryQuizRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizEditConfiguration {
    @Bean
    public QuizCreator quizCreator() {
        return new QuizCreator(new InMemoryQuizRepository());
    }
}
