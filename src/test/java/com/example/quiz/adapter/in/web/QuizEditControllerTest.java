package com.example.quiz.adapter.in.web;

import com.example.quiz.application.QuestionService;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizEditControllerTest {

    @Test
    void viewQuestionsCreatesModelWithQuestions() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(questionService);

        final Model model = new ConcurrentModel();
        final String viewName = quizController.viewQuestions(model);
        assertThat(viewName)
                .isEqualTo("view-questions");

        assertThat(model.containsAttribute("questions"))
                .isTrue();
    }

    @Test
    void addQuestionResultsInQuestionAddedAndRedirects() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(questionService);

        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        BindingResult bindingResult = new BeanPropertyBindingResult("objectName", "error");

        final String redirectPage = quizController.addQuestion(addQuestionForm, bindingResult);

        assertThat(redirectPage)
                .isEqualTo("redirect:/add-question");
        assertThat(questionRepository.findAll())
                .hasSize(1);
    }

    @Test
    void addQuestionWithoutACorrectChoiceReturnsForm() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(questionService);

        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                new ChoiceForm("a1", false),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false), "single");

        BindingResult bindingResult = new BeanPropertyBindingResult("objectName", "error");

        final String page = quizController.addQuestion(addQuestionForm, bindingResult);
        assertThat(page)
                .isEqualTo("add-question");
        assertThat(questionRepository.findAll())
                .isEmpty();
        assertThat(bindingResult.getGlobalErrors())
                .hasSize(1);
    }
}
