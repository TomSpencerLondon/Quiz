package com.example.quiz;

import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import com.example.quiz.domain.quiz.QuizSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuizApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizApplication.class, args);
  }
}
