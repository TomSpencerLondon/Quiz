package com.example.quiz.domain.quiz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.FinalMark;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class QuizSessionTest {
  @Test
  void emptyQuizThrowsException() {
    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(0);

    assertThatThrownBy(quiz::start)
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void sessionStartsWithTheFirstQuestion() {
    // Given
    final MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"),
        Collections.singletonList(new Answer("Answer 1")));

    final Question question = new Question("Question 1", choice);

    final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    questionRepository.save(question);

    Quiz quiz = new Quiz(questionRepository);

    // When
    QuizSession session = quiz.start();

    assertThat(session.question())
        .isEqualTo(question);
  }

  @Test
  void testTakerCanAddResponseToQuestionFromTheSession() {
    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(1);
    QuizSession session = quiz.start();

    // when
    Question question = session.question();
    session.respondWith("Answer 1", question);

    assertThat(session.lastResponseStatus())
        .isEqualTo(ResponseStatus.CORRECT);
  }

  @Test
//  testTakerCanCheckIfSessionWithOneQuestionIsFinished
  void givenQuizWithOneQuestionWhenQuestionIsAnsweredSessionIsFinished() {
    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(1);
    QuizSession session = quiz.start();
    Question question = session.question();

    // when
    session.respondWith("Answer 1", question);

    // Then
    assertThat(session.isFinished())
        .isTrue();
  }

  @Test
  // testTakerCanCheckIfSessionIsFinishedWithThreeQuestionsAfterSecondQuestion
  void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {

    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(3);
    QuizSession session = quiz.start();

    // when
    Question q1 = session.question();
    session.respondWith("Answer 1", q1);
    Question q2 = session.question();
    session.respondWith("Answer 2", q2);

    assertThat(session.isFinished())
        .isFalse();
  }

  @Test
  void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(3);
    QuizSession session = quiz.start();

    // when
    Question q1 = session.question();
    session.respondWith("Answer 1", q1);
    Question q2 = session.question();
    session.respondWith("Answer 2", q2);
    Question q3 = session.question();
    session.respondWith("Answer 2", q3);

    assertThat(session.isFinished())
        .isTrue();
  }

  @Test
  // respondWith_givesCorrectStatusForAnswer
  void quizWithOneQuestionWhenAnsweredCorrectlyThenReturnsCorrect() {
    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(1);
    final QuizSession quizSession = quiz.start();

    // When
    quizSession.respondWith("Answer 1", quizSession.question());

    // then
    assertThat(quizSession.lastResponseStatus())
        .isEqualByComparingTo(ResponseStatus.CORRECT);
  }

  // Ask Grade

  @Test
  void grade_gives_number_of_correct_responses_for_Session() {
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(3);
    QuizSession session = quiz.start();

    // when
    Question q1 = session.question();
    session.respondWith("Answer 1", q1);
    Question q2 = session.question();
    session.respondWith("Answer 2", q2);
    Question q3 = session.question();
    session.respondWith("Answer 2", q3);

    assertThat(session.correctResponsesCount())
        .isEqualTo(1L);
  }
  @Test
  void counts_incorrect_responses() {
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(3);
    QuizSession session = quiz.start();

    // when
    Question q1 = session.question();
    session.respondWith("Answer 1", q1);
    Question q2 = session.question();
    session.respondWith("Answer 2", q2);
    Question q3 = session.question();
    session.respondWith("Answer 2", q3);

    assertThat(session.incorrectResponsesCount())
        .isEqualTo(2L);
  }

  @Test
  void giveAGrade() {
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(3);
    QuizSession session = quiz.start();

    // when
    Question q1 = session.question();
    session.respondWith("Answer 1", q1);
    Question q2 = session.question();
    session.respondWith("Answer 2", q2);
    Question q3 = session.question();
    session.respondWith("Answer 2", q3);

    final Grade grade = new Grade(3, new FinalMark(1L, 2L));

    assertThat(session.grade())
        .isEqualTo(grade);
  }

  @Test
  void returnSameQuestionIfItHasntBeenAnswered() {
    // Given
    Quiz quiz = TestQuizFactory.createQuizWithQuestions(1);
    QuizSession session = quiz.start();

    // When
    Question q1 = session.question();
    Question q2 = session.question();

    // Then
    assertThat(q1)
        .isEqualTo(q2);
  }

  @Test
  void quizWithTwoQuestionsWhenResponseToFirstQuestionThenSecondQuestionIsCurrent() {
    final Quiz quiz = TestQuizFactory.createQuizWithQuestions(2);

    final QuizSession session = quiz.start();

    final Question q1 = session.question();
    session.respondWith("text", q1);

    final Question q2 = session.question();

    assertThat(q1)
        .isNotEqualTo(q2);
  }

}
