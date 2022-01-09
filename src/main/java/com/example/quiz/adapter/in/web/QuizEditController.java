package com.example.quiz.adapter.in.web;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizEditController {
  private final QuestionService questionService;

  public QuizEditController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/add-question")
  public String addQuestion(AddQuestionForm addQuestionForm) {
    final Question question = new Question(addQuestionForm.getText(),
        new SingleChoice(
            new Choice(addQuestionForm.getAnswer()),
            List.of(
                new Choice(addQuestionForm.getChoice1()),
                new Choice(addQuestionForm.getChoice2()),
                new Choice(addQuestionForm.getChoice3()),
                new Choice(addQuestionForm.getChoice4()))
        )
    );

    questionService.add(question);

    return "redirect:/add-question";
  }

  @GetMapping("/add-question")
  public String showAddQuestion(Model model) {
    model.addAttribute("addQuestionForm", new AddQuestionForm());
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
}