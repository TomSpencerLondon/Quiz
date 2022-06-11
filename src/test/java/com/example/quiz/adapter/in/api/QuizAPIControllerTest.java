package com.example.quiz.adapter.in.api;

import com.example.quiz.application.CreateQuestionService;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    CreateQuestionService createQuestionService;

    @Test
    void shouldExposeQuizResourceWithoutAuthentication() throws Exception {
        when(createQuestionService.findAll())
                .thenReturn(List.of(new Question("Q1",
                        new SingleChoice(
                                List.of(new Choice("Q1A1", true),
                                        new Choice("Q1A2", false),
                                        new Choice("Q1A3", false),
                                        new Choice("Q1A4", false))))));

        this.mockMvc.perform(get("/api/questions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)))
                    .andExpect(jsonPath("$[0].text", is("Q1")));
    }

    @Test
    void shouldAllowQuizCreationForAnonymousUser() throws Exception {
        this.mockMvc.perform(post("/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                {
                                  "choiceType": "single",
                                  "text": "Question 1 - Hello",
                                  "choices": [
                                  {
                                    "choice": "Answer 1",
                                    "correctAnswer": true
                                  },
                                  {
                                    "choice": "Answer 2",
                                    "correctAnswer": false
                                  },
                                  {
                                    "choice": "Answer 3",
                                    "correctAnswer": false
                                  },
                                  {
                                    "choice": "Answer 4",
                                    "correctAnswer": false
                                  }
                                  ]
                                }"""
                )
        ).andExpect(status().isCreated());
    }
}