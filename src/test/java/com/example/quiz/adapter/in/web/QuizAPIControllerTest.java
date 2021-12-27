package com.example.quiz.adapter.in.web;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuizAPIController.class)
class QuizAPIControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  QuestionService questionService;

  @Test
  void shouldExposeQuizResourceWithoutAuthentication() throws Exception {
    when(questionService.findAll())
        .thenReturn(List.of(new Question("Q1",
            new MultipleChoice(
                new Answer("Q1A1"),
                List.of(new Answer("Q1A1"),
                    new Answer("Q1A2"),
                    new Answer("Q1A3"),
                    new Answer("Q1A4"))))));

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
                      "answer": "Use html knowledge",
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