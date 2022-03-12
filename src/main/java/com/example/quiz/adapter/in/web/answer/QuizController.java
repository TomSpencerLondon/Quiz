package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizService;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {

    private QuizSession quizSession;
    private QuizService quizService;

    // for testing only
    QuizController(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
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
        final AskQuestionForm askQuestionForm = AskQuestionForm.from(question);
        model.addAttribute("askQuestionForm", askQuestionForm);

        if (question.isSingleChoice()) {
            return "single-choice";
        } else {
            return "multiple-choice";
        }

    }

    @PostMapping("/quiz")
    public String questionResponse(AskQuestionForm askQuestionForm) {
        quizSession.respondWith(askQuestionForm.getSelectedChoices());
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
        quizSession = quizService.createQuiz().start();
        return "redirect:/quiz";
    }
}
