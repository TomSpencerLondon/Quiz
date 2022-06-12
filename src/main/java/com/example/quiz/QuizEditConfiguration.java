package com.example.quiz;

import com.example.quiz.application.CreateQuestionService;
import com.example.quiz.application.QuizCreator;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuestionRepository;
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
