package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.QuizRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizSessionTest {

    @Test
    void sessionStartsWithTheFirstQuestion() {
        // Given
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        final ChoiceType choice = new SingleChoice(Collections.singletonList(new Choice("Answer 1", true)));

        Question question = questionRepository.save(new Question("Question 1", choice));
        QuestionId questionId = QuestionId.of(45L);
        question.setId(questionId);
        List<QuestionId> questionIds = List.of(question).stream().map(Question::getId).toList();
        QuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));

        // When
        QuizSession session = new QuizSession(question.getId(), "stub-1", quiz.getId());

        // Then
        assertThat(session.currentQuestionId())
                .isEqualTo(questionId);
    }

    @Test
//  testTakerCanCheckIfSessionWithOneQuestionIsFinished
    void givenQuizWithOneQuestionWhenQuestionIsAnsweredSessionIsFinished() {
        // Given
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question = questionRepository.save(
                new Question("Question 1",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        List<QuestionId> questionIds = Stream.of(question).map(Question::getId).toList();
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));
        QuizSession session = new QuizSession(question.getId(), "stub-1", quiz.getId());

        // when
        session.respondWith(question, quiz, question.choices().get(0).getId().id());

        // Then
        assertThat(session.isFinished(quiz))
                .isTrue();
    }

    @Test
    void quizWithThreeQuestionsWhenAnsweringTwoQuestionsSessionIsNotFinished() {
        // Given
        List<Choice> choices = List.of(
                new Choice("Answer 1", true)
        );
        ChoiceType choice = new SingleChoice(choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId(QuestionId.of((long) i));
            questions.add(q);
        }

        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question1 = questionRepository.save(questions.get(0));
        Question question2 = questionRepository.save(questions.get(1));
        questionRepository.save(questions.get(2));

        List<QuestionId> questionIds = questions.stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        QuizSession session = new QuizSession(questionIds.get(0), "stub-1", quiz.getId());

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
        List<Choice> choices = List.of(
                new Choice("Answer 1", true)
        );
        ChoiceType choice = new SingleChoice(choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId(QuestionId.of((long) i));
            questions.add(q);
        }

        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question1 = questionRepository.save(questions.get(0));
        Question question2 = questionRepository.save(questions.get(1));
        Question question3 = questionRepository.save(questions.get(2));
        List<QuestionId> questionIds = questions.stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        QuizSession session = new QuizSession(questionIds.get(0), "stub-1", quiz.getId());

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
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", false),
                new Choice("Answer 3", false)
        );
        ChoiceType choice = new SingleChoice(choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId(QuestionId.of((long) i));
            questions.add(q);
        }

        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question1 = questionRepository.save(questions.get(0));
        Question question2 = questionRepository.save(questions.get(1));
        Question question3 = questionRepository.save(questions.get(2));
        List<QuestionId> questionIds = questions.stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        QuizSession session = new QuizSession(questionIds.get(0), "stub-1", quiz.getId());

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
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", false),
                new Choice("Answer 3", false)
        );
        ChoiceType choice = new SingleChoice(choices);

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Question q = new Question("Question " + i, choice);
            q.setId(QuestionId.of((long) i));
            questions.add(q);
        }

        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question1 = questionRepository.save(questions.get(0));
        Question question2 = questionRepository.save(questions.get(1));
        Question question3 = questionRepository.save(questions.get(2));
        List<QuestionId> questionIds = questions.stream().map(Question::getId).toList();
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        QuizSession session = new QuizSession(questionIds.get(0), "stub-1", quiz.getId());


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
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question = questionRepository.save(
                new Question("Question 1",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        Question question2 = questionRepository.save(
                new Question("Question 2",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        Question question3 = questionRepository.save(
                new Question("Question 3",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        List<QuestionId> questionIds = Stream.of(question, question2, question3).map(Question::getId).toList();
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));

        final QuizSession session = new QuizSession(question.getId(), "Stub-1", quiz.getId());

        QuestionId questionId1 = session.currentQuestionId();
        Choice choice1 = question.choices().get(0);
        QuestionId questionId2 = session.currentQuestionId();
        Choice choice2 = question2.choices().get(1);
        QuestionId questionId3 = session.currentQuestionId();
        Choice choice3 = question3.choices().get(1);
        session.respondWith(question, quiz, choice1.getId().id());
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
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question1 = questionRepository.save(
                new Question("Question 1",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        Question question2 = questionRepository.save(
                new Question("Question 2",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        List<QuestionId> questionIds = Stream.of(question1, question2).map(Question::getId).toList();
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));

        final QuizSession session = new QuizSession(question1.getId(), "Stub-1", quiz.getId());

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
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question question = questionRepository.save(
                new Question("Question 1",
                new SingleChoice(List.of(new Choice("Correct Answer", true),
                        new Choice("Wrong Answer", false)))));
        Question question2 = questionRepository.save(
                new Question("Question 2",
                        new SingleChoice(List.of(new Choice("Correct Answer", true),
                                new Choice("Wrong Answer", false)))));
        List<QuestionId> questionIds = Stream.of(question, question2).map(Question::getId).toList();
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));

        final QuizSession session = new QuizSession(question.getId(), "Stub-1", quiz.getId());
        final QuestionId questionId1 = session.currentQuestionId();

        List<Long> longs = question.choices().stream().filter(Choice::isCorrect).map(Choice::getId).map(ChoiceId::id).toList();
        long[] choiceIds = new long[longs.size()];
        int count = 0;
        for (long i : longs) {
            choiceIds[count] = i;
            count++;
        }

        session.respondWith(question, quiz, choiceIds);

        // When
        final QuestionId questionId2 = session.currentQuestionId();

        // Then
        assertThat(questionId1)
                .isNotEqualTo(questionId2);
    }

    @Test
    void respondWithChoiceThenResponseHasSelectedChoice() {
        // Given
        InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        List<Choice> choices = List.of(
                new Choice(ChoiceId.of(44L), "Answer 1", true),
                new Choice(ChoiceId.of(74L), "Answer 2", false),
                new Choice(ChoiceId.of(37L), "Answer 3", false),
                new Choice(ChoiceId.of(55L), "Answer 4", false)
        );
        ChoiceType singleChoice = new SingleChoice(choices);
        Question question = questionRepository.save(new Question("Question 1", singleChoice));

        List<QuestionId> questionIds = Collections.singletonList(QuestionId.of(83L));
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz quiz = quizRepository.save(new Quiz("Quiz 1", questionIds));
        final QuizSession session = new QuizSession(question.getId(), "Stub-1", quiz.getId());

        // When
        Choice choice = choices.get(1);
        session.respondWith(question, quiz, question.choices().get(1).getId().id());

        // Then
        assertThat(session.responses().get(0).choices())
                .containsExactly(choice);

    }
}
