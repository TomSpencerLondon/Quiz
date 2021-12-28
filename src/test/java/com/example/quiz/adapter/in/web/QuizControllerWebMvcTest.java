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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@WebMvcTest(QuizController.class)
@Import(TestQuizConfiguration.class)
class QuizControllerWebMvcTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser(username = "tom")
  void questionEndpointExists() throws Exception {
    mockMvc.perform(
        get("/quiz")
    ).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "tom")
  void resultEndPointExists() throws Exception {
    mockMvc.perform(
        get("/result")
    ).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "tom")
  void restartEndpointExists() throws Exception {
    mockMvc.perform(
        post("/restart")
    ).andExpect(status().is3xxRedirection());
  }
}