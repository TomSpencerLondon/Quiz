package com.example.quiz;

import com.example.quiz.adapter.in.questionloader.FileQuestionParser;
import com.example.quiz.adapter.in.questionloader.QuestionParser;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories ("com.example.quiz.adapter.port.repository")
public class QuizApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuizApplication.class, args);
  }

  @Bean
  public Quiz quiz() throws URISyntaxException, IOException {
    final FileQuestionParser fileQuestionParser = new FileQuestionParser(new QuestionParser());
    final List<Question> questions = fileQuestionParser.read("questions.txt");
    return new Quiz(questions);
  }

}
