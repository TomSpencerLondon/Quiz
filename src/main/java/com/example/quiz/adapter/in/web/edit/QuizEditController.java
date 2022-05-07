package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.application.QuestionService;
import com.example.quiz.application.QuizCreator;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;
import com.example.quiz.domain.QuizId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizEditController {

    private final QuestionService questionService;
    private final QuizCreator quizCreator;
    @Value("${add-questions-base-number-of-choices}")
    private int baseNumberOfChoices;

    public QuizEditController(QuestionService questionService, QuizCreator quizCreator) {
        this.questionService = questionService;
        this.quizCreator = quizCreator;
    }

    @PostMapping("/add-question")
    public String addQuestion(@Valid AddQuestionForm addQuestionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-question";
        }
        try {
            questionService.add(addQuestionForm);
        } catch (NoCorrectChoiceSelected | TooManyCorrectChoicesSelected e) {
            ObjectError error = new ObjectError("Error", e.getMessage());
            bindingResult.addError(error);
            if (bindingResult.hasErrors()) {
                return "add-question";
            }
        }

        return "redirect:/add-question";
    }

    @GetMapping("/add-question")
    public String showAddQuestion(Model model) {

        AddQuestionForm addQuestionForm = new AddQuestionForm();
        // TODO: Make the number of default choices configurable
        // Use application.properties - to configure this value
        // The value must be nonnull and greater than zero
        // number of default choices
        // validation - needs to be positive and not zero
        // zero looks bad + not negative
        // Springboot wont start if validation fails
        // @value
        ChoiceForm[] choices = new ChoiceForm[baseNumberOfChoices];
        for (int i = 0; i < baseNumberOfChoices; i++) {
            choices[i] = new ChoiceForm();
        }

        addQuestionForm.setChoices(choices);
        model.addAttribute("addQuestionForm", addQuestionForm);
        return "add-question";
    }

    @GetMapping("/view-questions")
    public String viewQuestions(Model model) {
        final List<Question> questions = questionService.findAll();

        final List<QuestionView> questionViews = questions.stream()
                                                          .map(QuestionView::of)
                                                          .toList();

        model.addAttribute("questions", questionViews);
        return "view-questions";
    }

    @GetMapping("/add-choice")
    public String addChoice(Model model, @RequestParam("index") int index) {
        int nextIndex = index + 1;
        model.addAttribute("fieldNameChoiceText", "dummy.choices[" + index + "].choice");
        model.addAttribute("fieldNameCorrectAnswer", "dummy.choices[" + index + "].correctAnswer");
        model.addAttribute("hasErrors", false);
        model.addAttribute("index", nextIndex);
        model.addAttribute("dummy", new DummyQuestionChoices(nextIndex));

        return "fragments/form-fragments :: choice-input";
    }

    @PostMapping("/create-quiz")
    public String createQuiz(Model model) {
        QuestionId questionId = QuestionId.of(45L);
        List<QuestionId> questionIds = new ArrayList<>();
        questionIds.add(questionId);
        QuizId quizId = quizCreator.createQuiz("Quiz name", questionIds);

        return "redirect:/quiz?quizId=" + quizId.id();
    }

    @GetMapping("/quiz")
    public String quiz(Model model, @RequestParam(value = "quizId", defaultValue = "") String quizId) {
        return "quiz";
    }

    @GetMapping("/maker")
    public String maker(Model model) {
        List<Question> allQuestions = questionService.findAll();
        model.addAttribute("questions", allQuestions);
        return "maker";
    }

    @GetMapping(value = "/convert-markdown", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String convertMarkDown(@RequestParam(value = "text", defaultValue = "") String text) {
        MarkdownParser markdownParser = new MarkdownParser();
        return markdownParser.parse(text);
    }
}