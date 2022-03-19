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

    private QuizSessionService quizSessionService;
    private QuizSession quizSession;
    private final IdGenerator idGenerator;

    // for testing only
    QuizController(QuizSession quizSession, IdGenerator idGenerator) {
        this.quizSession = quizSession;
        this.idGenerator = idGenerator;
    }

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
            return "redirect:/start";
        }

        QuizSession quizSession = quizSessionService.findSessionById(id);
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
    public String questionResponse(AskQuestionForm askQuestionForm, @RequestParam(value = "id") String id) {
        if (id.isBlank()) {
            return "redirect:/start";
        }

        QuizSession quizSession = quizSessionService.findSessionById(id);
        quizSession.respondWith(askQuestionForm.getSelectedChoices());
        if (quizSession.isFinished()) {
            return "redirect:/result";
        }
        return "redirect:/quiz";
    }

    @GetMapping("/result")
    public String showResult(Model model, @RequestParam(value = "id") String id) {
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
}