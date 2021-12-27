package com.example.quiz.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuizAPIController.class)
class QuizAPIControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldExposeQuizResourceWithoutAuthentication() throws Exception {
    this.mockMvc.perform(get("/api/questions"))
        .andExpect(status().isOk());
  }
}