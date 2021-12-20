package com.example.quiz.domain.quiz;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import org.junit.jupiter.api.Test;

public class QuizFactoryTest {

  @Test
  void repository_with_one_question_then_quiz_is_created_with_one_question() {
    InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
    Question question = MultipleChoiceQuestionFactory.createMultipleChoiceQuestion();
    inMemoryQuestionRepository.save(question);

    final QuizFactory quizFactory = new QuizFactory(inMemoryQuestionRepository);
    final InMemoryQuiz quiz = quizFactory.createQuiz();


    assertThat(quiz.questions())
        .hasSize(1);
  }

}
