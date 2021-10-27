package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.port.InMemoryQuestionRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuizFactoryTest {

  @Test
  void repository_with_one_question_then_quiz_is_created_with_one_question() {
    InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
    Question question = new Question(
        "Question 1",
        new MultipleChoice(new Answer("Answer 1"),
            List.of(new Answer("Answer 1"), new Answer("Answer 2"))));
    inMemoryQuestionRepository.save(question);

    final QuizFactory quizFactory = new QuizFactory(inMemoryQuestionRepository);
    final Quiz quiz = quizFactory.createQuiz();


    assertThat(quiz.questions())
        .hasSize(1);
  }
}
