package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.adapter.out.web.initialChoiceCount.ChoiceCountConfig;
import com.example.quiz.application.CreateQuestionService;
import com.example.quiz.application.QuizCreator;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;
import com.example.quiz.domain.QuizId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizEditController {

    private final CreateQuestionService createQuestionService;
    private final QuizCreator quizCreator;
    private final QuestionFactory questionFactory;
    private ChoiceCountConfig choiceCountConfig;

    @Autowired
    SpringTemplateEngine templateEngine;


    public QuizEditController(CreateQuestionService createQuestionService, QuizCreator quizCreator, ChoiceCountConfig choiceCountConfig, QuestionFactory questionFactory) {
        this.createQuestionService = createQuestionService;
        this.quizCreator = quizCreator;
        this.choiceCountConfig = choiceCountConfig;
        this.questionFactory = questionFactory;
    }

    @PostMapping("/edit/add-question")
    public String addQuestion(@Valid AddQuestionForm addQuestionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-question";
        }
        try {
            // TODO: Transform addQuestionForm to String, numbers and domain objects
            Question question = questionFactory.transform(addQuestionForm);
            createQuestionService.add(question);
        } catch (NoCorrectChoiceSelected | TooManyCorrectChoicesSelected e) {
            ObjectError error = new ObjectError("Error", e.getMessage());
            bindingResult.addError(error);
            if (bindingResult.hasErrors()) {
                return "add-question";
            }
        }

        return "redirect:/edit/add-question";
    }

    @GetMapping("/edit/add-question")
    public String showAddQuestion(Model model) {
        Integer baseNumberOfChoices = choiceCountConfig.getBaseNumberOfChoices();
        AddQuestionForm addQuestionForm = new AddQuestionForm(baseNumberOfChoices);
        model.addAttribute("totalCount", baseNumberOfChoices);
        model.addAttribute("addQuestionForm", addQuestionForm);
        return "add-question";
    }

    @GetMapping(value = "/edit/add-choice")
    public String addChoice(Model model, @RequestParam("index") int index) {
        int nextIndex = index + 1;
        model.addAttribute("fieldNameChoiceText", "dummy.choices[" + index + "].choice");
        model.addAttribute("fieldNameCorrectAnswer", "dummy.choices[" + index + "].correctAnswer");
        model.addAttribute("hasErrors", false);
        model.addAttribute("index", nextIndex);
        model.addAttribute("dummy", new DummyQuestionChoices(nextIndex));
        model.addAttribute("totalCount", nextIndex);

        return "fragments/form-fragments :: another-choice-input";
    }

    @PostMapping("/edit/delete-choice")
    public ResponseEntity<Void> deleteChoice(@RequestParam("index") int index) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/edit/view-questions")
    public String viewQuestions(Model model) {
        final List<Question> questions = createQuestionService.findAll();

        final List<QuestionView> questionViews = questions.stream().map(QuestionView::of).toList();

        model.addAttribute("questions", questionViews);
        return "view-questions";
    }

    @PostMapping("/edit/create-quiz")
    public String createQuiz(Model model) {
        QuestionId questionId = QuestionId.of(45L);
        List<QuestionId> questionIds = new ArrayList<>();
        questionIds.add(questionId);
        QuizId quizId = quizCreator.createQuiz("Quiz name", questionIds);

        return "redirect:/quiz?quizId=" + quizId.id();
    }

    @GetMapping("/edit/quiz")
    public String quiz(Model model, @RequestParam(value = "quizId", defaultValue = "") String quizId) {
        return "quiz";
    }

    @GetMapping("/edit/maker")
    public String maker(Model model) {
        List<Question> allQuestions = createQuestionService.findAll();
        model.addAttribute("questions", allQuestions);
        return "maker";
    }

}