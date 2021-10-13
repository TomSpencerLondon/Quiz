package com.example.quiz;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizSessionTest {
  @Test
  void quizStartsASession() {
    // Given
    Quiz quiz = new Quiz();

    // When
    QuizSession session = quiz.start();

    // Then
    assertThat(session)
        .isNotNull();
  }

  @Test
  void sessionStartsWithTheFirstQuestion() {
    // Given
    final MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"),
        Collections.singletonList(new Answer("Answer 1")));

    final Question question = new Question("Question 1", choice, QuestionStatus.PENDING);
    Quiz quiz = new Quiz(question);

    // When
    QuizSession session = quiz.start();

    assertThat(session.question())
        .isEqualTo(question);
  }

  @Test
  void testTakerCanAddResponseToQuestionFromTheSession() {
    // Given
    final MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"),
        Collections.singletonList(new Answer("Answer 1")));

    Quiz quiz = new Quiz(new Question("Question 1", choice, QuestionStatus.PENDING));
    QuizSession session = quiz.start();

    // when
    session.respondWith("Answer 1");

    assertThat(session.isLastAnswerCorrect()).isTrue();
  }

  // After all questions
  // set up quiz question start respond - done
  // what query about session that could observe
  // ask quiz are there more questions? - single question => answer => ask session are you done?

  // after test one question - several questions
  // looping work

  // Later test - ask grade
}
