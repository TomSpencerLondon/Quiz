package com.example.quiz.adapter.in.web;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {

  private final QuestionService questionService;
  private WebQuizSession webQuizSession;
  private Question question;

  @Autowired
  public QuizController(QuestionService questionService, WebQuizSession webQuizSession) {
    this.questionService = questionService;
    this.webQuizSession = webQuizSession;
  }

  @PostMapping("/add-question")
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

  @GetMapping("/quiz")
  public String askQuestion(Model model) {
    question = webQuizSession.question();
    final AskQuestionForm askQuestionForm = AskQuestionForm.from(question);

    model.addAttribute("askQuestionForm", askQuestionForm);
    return "quiz";
  }

  @PostMapping("/quiz")
  public String answerQuestion(AskQuestionForm askQuestionForm) {
    webQuizSession.respondWith(askQuestionForm.getSelectedChoice(), question);

    return "redirect:/quiz";
  }
}
