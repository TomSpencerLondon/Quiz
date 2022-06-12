package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizSessionTest {

    @Test
    void sessionStartsWithTheFirstQuestion() {
        // Given
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question = questionRepository
                .save(new QuestionBuilder()
                        .withQuestionId(45L)
                        .withDefaultSingleChoice()
                        .build());
        Quiz quiz = new QuizBuilder()
                .withQuestions(question)
                .save();

        // When
        QuizSession session = new QuizSession(question.getId(), "stub-1", quiz.getId());

        // Then
        assertThat(session.currentQuestionId())
                .isEqualTo(QuestionId.of(45L));
    }

    @Test
//  testTakerCanCheckIfSessionWithOneQuestionIsFinished
    void givenQuizWithOneQuestionWhenQuestionIsAnsweredSessionIsFinished() {
        // Given
        QuestionBuilder questionBuilder = new QuestionBuilder();
        Question question = questionBuilder
                .withQuestionId(7L)
                .withDefaultSingleChoice()
                .save();
        Quiz quiz = new QuizBuilder()
                .withQuestions(question)
                .save();
        QuizSession session = new QuizSessionBuilder().withQuestion(question).withQuiz(quiz).build();

        // when
        session.respondWith(question, quiz, questionBuilder.firstChoiceIdForQuestion().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder()
                .withQuestions(question1, question2, question3)
                .build();
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, questionBuilder1.firstChoiceIdForQuestion().id());
        session.respondWith(question2, quiz, questionBuilder2.firstChoiceIdForQuestion().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isFalse();
    }

    @Test
    void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder()
                .withQuestions(question1, question2, question3)
                .build();
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, questionBuilder1.firstChoiceIdForQuestion().id());
        session.respondWith(question2, quiz, questionBuilder2.firstChoiceIdForQuestion().id());
        session.respondWith(question3, quiz, questionBuilder3.firstChoiceIdForQuestion().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void grade_gives_number_of_correct_responses_for_Session() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder()
                .withQuestions(question1, question2, question3)
                .build();
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, questionBuilder1.correctChoiceForQuestion().id());
        session.respondWith(question2, quiz, questionBuilder2.incorrectChoiceForQuestion().id());
        session.respondWith(question3, quiz, questionBuilder3.incorrectChoiceForQuestion().id());

        // Then
        assertThat(session.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void counts_incorrect_responses() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder()
                .withQuestions(question1, question2, question3)
                .build();
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1)
                .withQuiz(quiz)
                .build();

        // when
        session.respondWith(question1, quiz, questionBuilder1.incorrectChoiceForQuestion().id());
        session.respondWith(question2, quiz, questionBuilder2.incorrectChoiceForQuestion().id());
        session.respondWith(question3, quiz, questionBuilder3.correctChoiceForQuestion().id());

        // Then
        assertThat(session.incorrectResponsesCount())
                .isEqualTo(2L);
    }

    @Test
    void calculatesGradeForFinishedTest() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder2 = new QuestionBuilder();
        Question question2 = questionBuilder2
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        QuestionBuilder questionBuilder3 = new QuestionBuilder();
        Question question3 = questionBuilder3
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder()
                .withQuestions(question1, question2, question3)
                .build();
        QuizSession session = new QuizSessionBuilder()
                .withQuestion(question1).withQuiz(quiz).build();

        // When
        session.respondWith(question1, quiz, questionBuilder1.correctChoiceForQuestion().id());
        session.respondWith(question2, quiz, questionBuilder2.incorrectChoiceForQuestion().id());
        session.respondWith(question3, quiz, questionBuilder3.incorrectChoiceForQuestion().id());

        // Then
        Grade expectedGrade = new GradeBuilder()
                .withCorrectResponseFor(question1)
                .withIncorrectResponseFor(question2)
                .withIncorrectResponseFor(question3)
                .build();
        final Grade result = session.grade();
        assertThat(result)
                .isEqualTo(expectedGrade);
    }

    @Test
    void returnSameQuestionIfItHasntBeenAnswered() {
        // Given
        Question question1 = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        Question question2 = new QuestionBuilder()
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder().withQuestions(question1, question2, question3).build();
        QuizSession session = new QuizSessionBuilder().withQuestion(question1).withQuiz(quiz).build();

        // When
        final QuestionId questionId1 = session.currentQuestionId();
        QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isEqualTo(questionId2);
    }

    @Test
    void quizWithTwoQuestionsWhenResponseToFirstQuestionThenSecondQuestionIsCurrent() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question1 = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();
        Question question2 = new QuestionBuilder()
                .withQuestionId(2L)
                .withDefaultSingleChoice()
                .save();
        Question question3 = new QuestionBuilder()
                .withQuestionId(3L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder().withQuestions(question1, question2, question3).build();
        QuizSession session = new QuizSessionBuilder().withQuestion(question1).withQuiz(quiz).build();
        final QuestionId questionId1 = session.currentQuestionId();
        session.respondWith(question1, quiz, questionBuilder1.firstChoiceIdForQuestion().id());

        // When
        final QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isNotEqualTo(questionId2);
    }

    @Test
    void respondWithChoiceThenResponseHasSelectedChoice() {
        // Given
        QuestionBuilder questionBuilder1 = new QuestionBuilder();
        Question question = questionBuilder1
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder().withQuestions(question).build();
        QuizSession session = new QuizSessionBuilder().withQuestion(question).withQuiz(quiz).build();
        Choice choice = question.choices().get(0);

        // When
        session.respondWith(question, quiz, questionBuilder1.firstChoiceIdForQuestion().id());

        // Then
        assertThat(session.responses().get(0).choices())
                .containsExactly(choice);
    }
}
