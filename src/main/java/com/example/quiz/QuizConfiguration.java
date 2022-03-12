package com.example.quiz;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QuizConfiguration {

    @Bean
    Quiz createQuiz(QuestionRepository questionRepository) {
        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }

}
