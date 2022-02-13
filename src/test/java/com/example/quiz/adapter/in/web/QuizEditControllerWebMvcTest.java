package com.example.quiz.adapter.in.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@WebMvcTest(QuizEditController.class)
public class QuizEditControllerWebMvcTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  QuestionService questionService;

  @Test
  @WithMockUser(username = "tom")
  void addQuestionRedirects() throws Exception {
    mockMvc.perform(post("/add-question")
        .with(csrf())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("text", "Q1")
        .param("choice1.choice", "Q1A1")
        .param("choice1.correctAnswer", "true")
        .param("choice2.choice", "Q1A2")
        .param("choice2.correctAnswer", "false")
        .param("choice3.choice", "Q1A3")
        .param("choice3.correctAnswer", "false")
        .param("choice4.choice", "Q1A4")
        .param("choice4.correctAnswer", "false")
    ).andExpect(status().is3xxRedirection());
  }

  @Test
  @WithMockUser(username = "tom")
  void shouldHaveFormObjectInModel() throws Exception {
    mockMvc.perform(
            get("/add-question")
        ).andExpect(view().name("add-question"))
        .andExpect(model().attributeExists("addQuestionForm"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "tom")
  void shouldShowAllQuestions() throws Exception {
    mockMvc.perform(
        get("/view-questions")
    ).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "tom")
  void formWithoutQuestionAndMissingChoiceShowsErrors() throws Exception {
    mockMvc.perform(post("/add-question")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("text", "")
            .param("choice1.choice", "")
            .param("choice1.correctAnswer", "true")
            .param("choice2.choice", " ")
            .param("choice2.correctAnswer", "false")
            .param("choice3.choice", " ")
            .param("choice3.correctAnswer", "false")
            .param("choice4.choice", "Q1A4")
            .param("choice4.correctAnswer", "false"))
        .andExpect(view().name("add-question"))
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("addQuestionForm", "text"))
        .andExpect(model().attributeHasFieldErrors("addQuestionForm", "choice1.choice"))
        .andExpect(model().attributeHasFieldErrors("addQuestionForm", "choice2.choice"))
        .andExpect(model().attributeHasFieldErrors("addQuestionForm", "choice3.choice"));
  }

  @Test
  @WithMockUser(username = "tom")
  void formWithTooManyCorrectAnswersSelectedShowsErrors() throws Exception {
    mockMvc.perform(post("/add-question")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("text", "Q1")
            .param("choice1.choice", "Q1A1")
            .param("choice1.correctAnswer", "true")
            .param("choice2.choice", "Q1A2")
            .param("choice2.correctAnswer", "true")
            .param("choice3.choice", "Q1A3")
            .param("choice3.correctAnswer", "false")
            .param("choice4.choice", "Q1A4")
            .param("choice4.correctAnswer", "false"))
        .andExpect(view().name("add-question"))
        .andExpect(model().hasErrors());
  }
}
