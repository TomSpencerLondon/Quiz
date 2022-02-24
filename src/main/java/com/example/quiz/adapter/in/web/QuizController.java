package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {

    private QuizSession quizSession;
    private Quiz quiz;

    public QuizController(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    @Autowired
    public QuizController(Quiz quiz) {
        this.quiz = quiz;
    }

    @GetMapping("/")
    public String home() {
        return "start";
    }

    @GetMapping("/quiz")
    public String askQuestion(Model model) {
        if (quizSession.isFinished()) {
            return "redirect:/result";
        }
        Question question = quizSession.question();

        if (question.isSingleChoice()) {
            final AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = AskSingleChoiceQuestionForm.from(question);
            model.addAttribute("askSingleChoiceQuestionForm", askSingleChoiceQuestionForm);
            return "single-choice";
        } else {
            // AskMultipleChoiceQuestionForm
            // Test for Multiple Choice
            return "multiple-choice";
        }

    }

    @PostMapping("/quiz")
    public String questionResponse(AskSingleChoiceQuestionForm askSingleChoiceQuestionForm) {
        final Choice selectedChoice = new Choice(askSingleChoiceQuestionForm.getSelectedChoice());
        quizSession.respondWith(selectedChoice);
        if (quizSession.isFinished()) {
            return "redirect:/result";
        }
        return "redirect:/quiz";
    }

    @GetMapping("/result")
    public String showResult(Model model) {
        Grade grade = quizSession.grade();
        model.addAttribute("resultView", ResultView.from(grade));
        return "result";
    }

    @PostMapping("/start")
    public String start() {
        quizSession = quiz.start();
        return "redirect:/quiz";
    }
}
