package com.example.quiz;

import com.example.quiz.adapter.in.web.WebQuiz;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.QuizSession;
import com.example.quiz.domain.quiz.Quiz;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuizApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizApplication.class, args);
  }

  @Bean
  QuizSession createQuizSession(QuestionRepository questionRepository){
    final Quiz quiz = new WebQuiz(questionRepository);
    return new QuizSession(quiz);
  }
}
