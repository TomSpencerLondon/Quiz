package com.example.quiz.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class AddQuestionIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  QuestionService questionService;

  @Test
  void addQuestionRedirects() throws Exception {
    mockMvc.perform(post("/quiz")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("text", "Q1")
        .param("answer", "Q1A1")
        .param("choice1", "Q1A1")
        .param("choice2", "Q1A2")
        .param("choice3", "Q1A3")
        .param("choice4", "Q1A4")
    ).andExpect(status().is3xxRedirection());
  }
}