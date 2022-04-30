package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.Grade;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;
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
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizController(QuizSessionService quizSessionService, TokenGenerator tokenGenerator, QuestionRepository questionRepository) {
        this.quizSessionService = quizSessionService;
        this.tokenGenerator = tokenGenerator;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String home() {
        return "start";
    }

    @GetMapping("/question")
    public String askQuestion(Model model, @RequestParam(value = "token", defaultValue = "") String token) {
        if (token.isBlank()) {
            return "redirect:/";
        }

        // need to return optional here + orElseThrow()
        QuizSession quizSession = quizSessionService.findSessionByToken(token);
        if (quizSession.isFinished()) {
            return "redirect:/result?token=" + token;
        }

        QuestionId questionId = quizSession.currentQuestionId();
        Question question = questionRepository.findById(questionId).orElseThrow();
        final AskQuestionForm askQuestionForm = AskQuestionForm.from(question);
        model.addAttribute("askQuestionForm", askQuestionForm);
        model.addAttribute("token", token);
        return templateFor(question);
    }

    @PostMapping("/question")
    public String questionResponse(AskQuestionForm askQuestionForm, @RequestParam(value = "token") String token) {
        QuizSession quizSession = quizSessionService.findSessionByToken(token);
        quizSession.respondWith(askQuestionForm.getSelectedChoices());
        if (quizSession.isFinished()) {
            return "redirect:/result?token=" + token;
        }
        return "redirect:/question?token=" + token;
    }

    @GetMapping("/result")
    public String showResult(Model model, @RequestParam(value = "token", defaultValue = "") String token) {
        if (token.isBlank()) {
            return "redirect:/";
        }

        Grade grade = quizSessionService.findSessionByToken(token).grade();
        model.addAttribute("resultView", ResultView.from(grade, questionRepository));
        return "result";
    }

    @PostMapping("/start")
    public String start() {
        String token = tokenGenerator.token();
        quizSessionService.startSessionWithToken(token);
        return "redirect:/question?token=" + token;
    }

    private String templateFor(Question question) {
        if (question.isSingleChoice()) {
            return "single-choice";
        } else {
            return "multiple-choice";
        }
    }
}