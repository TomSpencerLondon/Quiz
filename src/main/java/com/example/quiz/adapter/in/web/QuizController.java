package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizController {

  private final QuestionService questionService;

  @Autowired
  public QuizController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/question")
  public String addQuestion(AddQuestionForm addQuestionForm) {

    final Question question = new Question(addQuestionForm.getText(),
        new MultipleChoice(
            new Answer(addQuestionForm.getAnswer()),
            List.of(
                new Answer(addQuestionForm.getChoice1()),
                new Answer(addQuestionForm.getChoice2()),
                new Answer(addQuestionForm.getChoice3()),
                new Answer(addQuestionForm.getChoice4()))
        )
    );

    questionService.add(question);

    return "redirect:/quiz";
  }

  @GetMapping("/quiz")
  public String getQuiz(Model model) {
    model.addAttribute("addQuestionForm", new AddQuestionForm());
    return "quiz";
  }
}
