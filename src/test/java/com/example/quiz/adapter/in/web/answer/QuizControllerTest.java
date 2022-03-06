package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerTest {

    @Test
    void afterQuizStartedAskForQuestionReturnsFirstQuestion() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(SingleChoiceQuestionTestFactory.createSingleChoiceQuestion());
        List<Question> questions = questionRepository.findAll();
        Quiz quiz = new Quiz(questions);
        QuizController quizController = new QuizController(quiz);
        quizController.start();

        final Model model = new ConcurrentModel();

        quizController.askQuestion(model);

        final AskQuestionForm question = (AskQuestionForm) model.getAttribute("askQuestionForm");

        assertThat(question.getQuestion())
                .isEqualTo("Question 1");
    }

    @Test
    void storesFormResponseAnswerInQuizSessionMarkedAsCorrectAnswer() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Correct Answer", true), new Choice("Wrong Answer", false))));
        List<Question> questions = List.of(question);
        Quiz quiz = new Quiz(questions);
        QuizSession quizSession = quiz.start();
        QuizController quizController = new QuizController(quizSession);
        final Model model = new ConcurrentModel();
        quizController.askQuestion(model);

        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm);

        assertThat(quizSession.correctResponsesCount())
                .isEqualTo(1L);
    }

    @Test
    void afterRespondingToLastQuestionShowsResults() {
        QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();

        final Model model = new ConcurrentModel();

        quizController.askQuestion(model);
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        final String redirectPage = quizController.questionResponse(askQuestionForm);

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }

    @Test
    void showsResultOnPage() {
        final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();
        quizController.showResult(model);

        assertThat(model.containsAttribute("resultView")).isTrue();
    }

    @Test
    void askQuestionTwiceGoesToSamePage() {
        // Given
        final QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
        final Model model = new ConcurrentModel();
        quizController.askQuestion(model);

        // When
        final String page = quizController.askQuestion(model);

        // Then
        assertThat(page)
                .isEqualTo("single-choice");
    }

    @Test
    void askingQuestionOnAFinishedQuizReturnsResult() {
        QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
        ConcurrentModel model = new ConcurrentModel();
        quizController.askQuestion(model);
        AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setSelectedChoices(0);
        quizController.questionResponse(askQuestionForm);
        String redirectPage = quizController.askQuestion(model);

        assertThat(redirectPage)
                .isEqualTo("redirect:/result");
    }


    @Test
    void afterStartCreateSessionAndRedirectToQuiz() {
        QuizController quizController = createAndStartQuizControllerWithOneSingleChoiceQuestion();
        String redirect = quizController.start();
        assertThat(redirect)
                .isEqualTo("redirect:/quiz");
    }

    @Test
    void multipleChoiceQuestionReturnsMultipleChoicePage() {
        QuizController quizController = createAndStartMultipleChoiceQuizController();
        ConcurrentModel model = new ConcurrentModel();

        String redirect = quizController.askQuestion(model);

        assertThat(redirect)
                .isEqualTo("multiple-choice");
        assertThat(model.containsAttribute("askQuestionForm"))
                .isTrue();
    }

    @NotNull
    private QuizController createAndStartMultipleChoiceQuizController() {
        Question multipleChoiceQuestion = MultipleChoiceQuestionTestFactory.multipleChoiceQuestion();
        List<Question> questions = List.of(multipleChoiceQuestion);
        final Quiz quiz = new Quiz(questions);
        final QuizController quizController = new QuizController(quiz);
        quizController.start();
        return quizController;
    }

    private QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
        Quiz quiz = TestQuizFactory.createQuizWithSingleChoiceQuestions(1);
        QuizController quizController = new QuizController(quiz);
        quizController.start();
        return quizController;
    }
}
