package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.QuizRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

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
        List<QuestionId> questionIds = Stream.of(question).map(Question::getId).toList();
        QuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));

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
        Question question = new QuestionBuilder()
                .withQuestionId(7L)
                .withDefaultSingleChoice()
                .save();
        Quiz quiz = new QuizBuilder()
                .withQuestions(question)
                .save();
        QuizSession session = new QuizSessionBuilder().withQuestion(question).withQuiz(quiz).build();

        // when
        session.respondWith(question, quiz, question.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {
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

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isFalse();
    }

    @Test
    void testTakerCanCheckIfSessionWithThreeQuestionsIsFinishedAfterThirdQuestion() {
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

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(0).getId().id());
        session.respondWith(question3, quiz, question2.choices().get(0).getId().id());


        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void grade_gives_number_of_correct_responses_for_Session() {
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

        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(1).getId().id());
        session.respondWith(question3, quiz, question2.choices().get(2).getId().id());

        // Then
        assertThat(session.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void counts_incorrect_responses() {
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


        // when
        session.respondWith(question1, quiz, question1.choices().get(0).getId().id());
        session.respondWith(question2, quiz, question2.choices().get(1).getId().id());
        session.respondWith(question3, quiz, question2.choices().get(2).getId().id());

        // Then
        assertThat(session.incorrectResponsesCount())
                .isEqualTo(2L);
    }

    @Test
    void calculatesGradeForFinishedTest() {
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

        QuestionId questionId1 = session.currentQuestionId();
        Choice choice1 = question1.choices().get(0);
        QuestionId questionId2 = session.currentQuestionId();
        Choice choice2 = question2.choices().get(1);
        QuestionId questionId3 = session.currentQuestionId();
        Choice choice3 = question3.choices().get(1);
        session.respondWith(question1, quiz, choice1.getId().id());
        session.respondWith(question2, quiz, choice2.getId().id());
        session.respondWith(question3, quiz, choice3.getId().id());

        List<Response> responses = List.of(
                new Response(questionId1, true, choice1),
                new Response(questionId2, false, choice2),
                new Response(questionId3, false, choice3));

        // When
        final Grade grade = new Grade(responses, 1, 2);

        // Then
        final Grade result = session.grade();
        assertThat(result)
                .isEqualTo(grade);
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
        final QuestionId questionId1 = session.currentQuestionId();
        long[] choiceIds = question1
                .choices()
                .stream()
                .filter(Choice::isCorrect)
                .map(Choice::getId)
                .mapToLong(ChoiceId::id).toArray();

        session.respondWith(question1, quiz, choiceIds);

        // When
        final QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isNotEqualTo(questionId2);
    }

    @Test
    void respondWithChoiceThenResponseHasSelectedChoice() {
        // Given
        Question question = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .save();

        Quiz quiz = new QuizBuilder().withQuestions(question).build();
        QuizSession session = new QuizSessionBuilder().withQuestion(question).withQuiz(quiz).build();

        // When
        Choice choice = question.choices().get(1);
        session.respondWith(question, quiz, choice.getId().id());

        // Then
        assertThat(session.responses().get(0).choices())
                .containsExactly(choice);

    }
}
