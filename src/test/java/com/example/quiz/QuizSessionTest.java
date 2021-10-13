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

    assertThat(session.isLastAnswerCorrect())
        .isTrue();
  }

  @Test
  void testTakerCanCheckIfSessionWithOneQuestionIsFinished() {
    final MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"),
        Collections.singletonList(new Answer("Answer 1")));

    Quiz quiz = new Quiz(new Question("Question 1", choice, QuestionStatus.PENDING));
    QuizSession session = quiz.start();

    // when
    session.respondWith("Answer 1");

    assertThat(session.isFinished())
        .isTrue();
  }

  @Test
  void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterSecondQuestion() {
    List<Answer> answers = List.of(
        new Answer("Answer 1")
    );

    MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"), answers);

    Question question1 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Question question2 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Question question3 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Quiz quiz = new Quiz(question1, question2, question3);
    QuizSession session = quiz.start();

    // when
    session.respondWith("Answer 1");
    session.respondWith("Answer 2");

    assertThat(session.isFinished())
        .isFalse();
  }

  @Test
  void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
    List<Answer> answers = List.of(
        new Answer("Answer 1")
    );

    MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"), answers);

    Question question1 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Question question2 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Question question3 = new Question("Question 1", choice, QuestionStatus.PENDING);
    Quiz quiz = new Quiz(question1, question2, question3);
    QuizSession session = quiz.start();

    // when
    session.respondWith("Answer 1");
    session.respondWith("Answer 2");
    session.respondWith("Answer 2");

    assertThat(session.isFinished())
        .isTrue();
  }

  // Later test - ask grade
}
