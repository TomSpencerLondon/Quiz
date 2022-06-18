package com.example.quiz;

import com.example.quiz.hexagon.application.CreateQuestionService;
import com.example.quiz.hexagon.application.QuizCreator;
import com.example.quiz.hexagon.application.port.InMemoryQuizRepository;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizEditConfiguration {
    @Bean
    public QuizCreator quizCreator() {
        return new QuizCreator(new InMemoryQuizRepository());
    }

    @Bean
    public CreateQuestionService createQuestionService(QuestionRepository questionRepository) {
        return new CreateQuestionService(questionRepository);
    }

}
