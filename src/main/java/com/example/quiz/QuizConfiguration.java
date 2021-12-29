package com.example.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizConfiguration {

  @Bean
  Quiz createQuiz(QuestionRepository questionRepository) {
    questionRepository.save(new Question(
        "Question 1",
        new MultipleChoice(new Answer("Answer 1"),
            List.of(
                new Answer("Answer 1"),
                new Answer("Answer 2"),
                new Answer("Answer 3"),
                new Answer("Answer 4")
                )
    )));
    return new Quiz(questionRepository);
  }

}
