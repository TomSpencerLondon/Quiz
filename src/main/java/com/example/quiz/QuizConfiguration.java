package com.example.quiz;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Quiz;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizConfiguration {

    @Bean
    Quiz createQuiz(QuestionRepository questionRepository) {
        return new Quiz(questionRepository);
    }

}
