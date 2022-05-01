package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.application.QuestionService;
import com.example.quiz.application.QuizCreator;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizEditControllerTest {

    private static final QuizCreator DUMMY_QUIZ_CREATOR = null;

    @Test
    void viewQuestionsCreatesModelWithQuestions() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(questionRepository);
        QuizEditController quizController = new QuizEditController(questionService, DUMMY_QUIZ_CREATOR);

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
        QuizEditController quizController = new QuizEditController(questionService, DUMMY_QUIZ_CREATOR);

        final AddQuestionForm addQuestionForm = new AddQuestionForm(
                "question",
                "single", new ChoiceForm("a1", true),
                new ChoiceForm("a2", false),
                new ChoiceForm("a3", false),
                new ChoiceForm("a4", false));

        BindingResult bindingResult = new BeanPropertyBindingResult("objectName", "error");

        final String redirectPage = quizController.addQuestion(addQuestionForm, bindingResult);

        assertThat(redirectPage)
                .isEqualTo("redirect:/add-question");
        assertThat(questionRepository.findAll())
                .hasSize(1);
    }
}
