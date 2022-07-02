package com.tomspencerlondon.quiz;

import com.tomspencerlondon.quiz.hexagon.application.CreateQuestionService;
import com.tomspencerlondon.quiz.hexagon.application.QuizCreator;
import com.tomspencerlondon.quiz.hexagon.application.port.InMemoryQuizRepository;
import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
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
