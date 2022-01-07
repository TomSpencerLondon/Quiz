package com.example.quiz.adapter.in.web;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizEditController {
  private final QuestionService questionService;

  public QuizEditController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/add-question")
  public String addQuestion(AddQuestionForm addQuestionForm, @RequestParam List<String> correctAnswers) {
    List<Answer> answers = correctAnswers.stream()
        .map(Integer::parseInt)
        .map(index -> addQuestionForm.getChoices().get(index - 1))
        .map(Answer::new).toList();

    final Question question = new Question(addQuestionForm.getText(),
        new MultipleChoice(
            answers.get(0),
            List.of(
                new Answer(addQuestionForm.getChoice1()),
                new Answer(addQuestionForm.getChoice2()),
                new Answer(addQuestionForm.getChoice3()),
                new Answer(addQuestionForm.getChoice4()))
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