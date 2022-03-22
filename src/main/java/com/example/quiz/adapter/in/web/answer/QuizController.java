package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.IdGenerator;
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
    private final IdGenerator idGenerator;

    @Autowired
    public QuizController(QuizSessionService quizSessionService, IdGenerator idGenerator) {
        this.quizSessionService = quizSessionService;
        this.idGenerator = idGenerator;
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

        QuizSession quizSession = quizSessionService.findSessionById(id);
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
        QuizSession quizSession = quizSessionService.findSessionById(id);
        quizSession.respondWith(askQuestionForm.getSelectedChoices());
        if (quizSession.isFinished()) {
            return "redirect:/result?id=" + id;
        }
        return "redirect:/quiz?id=" + id;
    }

    @GetMapping("/result")
    public String showResult(Model model, @RequestParam(value = "id", defaultValue = "") String id) {
        if (id.isBlank()) {
            return "redirect:/";
        }

        Grade grade = quizSessionService.findSessionById(id).grade();
        model.addAttribute("resultView", ResultView.from(grade));
        return "result";
    }

    @PostMapping("/start")
    public String start() {
        String id = idGenerator.newId();
        quizSessionService.startSessionWithId(id);
        return "redirect:/quiz?id=" + id;
    }

    private String templateFor(Question question) {
        if (question.isSingleChoice()) {
            return "single-choice";
        } else {
            return "multiple-choice";
        }
    }
}