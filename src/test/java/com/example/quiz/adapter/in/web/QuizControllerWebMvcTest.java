package com.example.quiz.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.quiz.application.QuestionService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@WebMvcTest
class QuizControllerWebMvcTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  QuestionService questionService;

  @Test
  void addQuestionRedirects() throws Exception {
    mockMvc.perform(post("/create-question")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("text", "Q1")
        .param("answer", "Q1A1")
        .param("choice1", "Q1A1")
        .param("choice2", "Q1A2")
        .param("choice3", "Q1A3")
        .param("choice4", "Q1A4")
    ).andExpect(status().is3xxRedirection());
  }

  @Test
  void shouldHaveFormObjectInModel() throws Exception {
    mockMvc.perform(
        get("/create-question")
    ).andExpect(view().name("quiz"))
        .andExpect(model().attributeExists("addQuestionForm"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldShowAllQuestions() throws Exception {
    mockMvc.perform(
        get("/view-questions")
    ).andExpect(status().isOk());
  }
}