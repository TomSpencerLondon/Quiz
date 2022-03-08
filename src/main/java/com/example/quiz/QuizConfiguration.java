package com.example.quiz;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class QuizConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizConfiguration.class);

    @Bean
    @Scope("prototype")
    Quiz createQuiz(QuestionRepository questionRepository) {
        LOGGER.info("creating instance of quiz");
        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }

}
