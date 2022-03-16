package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.QuizSessionServiceTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizControllerIdTest {
    @Test
    void askQuestionWithoutIdThenRedirectsWithId() {
        QuizSessionService quizSessionService = QuizSessionServiceTestFactory.createQuizSessionService();
        QuizController quizController = new QuizController(quizSessionService, new StubIdGenerator());
        quizController.start();

        final Model model = new ConcurrentModel();
        String redirectPage = quizController.askQuestion(model, "");
        assertThat(redirectPage)
                .isEqualTo("redirect:/?id=stub-id-1");
    }
}
