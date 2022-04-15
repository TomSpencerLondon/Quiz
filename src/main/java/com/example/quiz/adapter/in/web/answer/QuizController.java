package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    private final QuizSessionService quizSessionService;
    private final TokenGenerator tokenGenerator;

    @Autowired
    public QuizController(QuizSessionService quizSessionService, TokenGenerator tokenGenerator) {
        this.quizSessionService = quizSessionService;
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping("/")
    public String home() {
        return "start";
    }

    @GetMapping("/quiz")
    public String askQuestion(Model model, @RequestParam(value = "id", defaultValue = "") String id) {
        if (id.isBlank()) {
            return "redirect:/";
        }

        QuizSession quizSession = quizSessionService.findSessionByToken(id);
        if (quizSession.isFinished()) {
            return "redirect:/result?id=" + id;
        }

        Question question = quizSession.question();
        final AskQuestionForm askQuestionForm = AskQuestionForm.from(question);
        model.addAttribute("askQuestionForm", askQuestionForm);
        model.addAttribute("id", id);
        return templateFor(question);
    }

    @PostMapping("/quiz")
    public String questionResponse(AskQuestionForm askQuestionForm, @RequestParam(value = "id") String id) {
        QuizSession quizSession = quizSessionService.findSessionByToken(id);
        quizSession.respondWith(askQuestionForm.getSelectedChoices());
        if (quizSession.isFinished()) {
            return "redirect:/result?id=" + id;
        }
        return "redirect:/quiz?id=" + id;
    }

    @GetMapping("/result")
    public String showResult(Model model, @RequestParam(value = "token", defaultValue = "") String token) {
        if (token.isBlank()) {
            return "redirect:/";
        }

        Grade grade = quizSessionService.findSessionByToken(token).grade();
        model.addAttribute("resultView", ResultView.from(grade));
        return "result";
    }

    @PostMapping("/start")
    public String start() {
        String token = tokenGenerator.token();
        quizSessionService.startSessionWithId(token);
        return "redirect:/quiz?id=" + token;
    }

    private String templateFor(Question question) {
        if (question.isSingleChoice()) {
            return "single-choice";
        } else {
            return "multiple-choice";
        }
    }
}