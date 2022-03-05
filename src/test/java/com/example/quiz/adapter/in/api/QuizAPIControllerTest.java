package com.example.quiz.adapter.in.api;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizAPIController.class)
@Tag("integration")
class QuizAPIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    @Test
    void shouldExposeQuizResourceWithoutAuthentication() throws Exception {
        when(questionService.findAll())
                .thenReturn(List.of(new Question("Q1",
                        new SingleChoice(
                                new Choice("Q1A1"),
                                List.of(new Choice("Q1A1"),
                                        new Choice("Q1A2"),
                                        new Choice("Q1A3"),
                                        new Choice("Q1A4"))))));

        this.mockMvc.perform(get("/api/questions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)))
                    .andExpect(jsonPath("$[0].text", is("Q1")));
    }

    @Test
    void shouldAllowQuizCreationForAnonymousUser() throws Exception {
        this.mockMvc.perform(post("/api/questions")
                .content(
                        """
                                    {
                                      "text": "How do you create a form",
                                      "answerDbo": "Use html knowledge",
                                      "choice1": "Give up!",
                                      "choice2": "Go to bed!",
                                      "choice3": "Go for a walk",
                                      "choice4": "Use html knowledge"
                                    }
                                """
                )
        ).andExpect(status().isCreated());
    }
}