package com.example.quiz.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@WebMvcTest(QuizController.class)
@Import(TestQuizConfiguration.class)
@WithMockUser(username = "tom")
class QuizControllerWebMvcTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void questionEndpointExists() throws Exception {
    mockMvc.perform(
        get("/quiz")
    ).andExpect(status().isOk());
  }

  @Test
  void resultEndPointExists() throws Exception {
    mockMvc.perform(
        get("/result")
    ).andExpect(status().isOk());
  }

  @Test
  void restartEndpointExists() throws Exception {
    mockMvc.perform(
        post("/restart")
    ).andExpect(status().is3xxRedirection());
  }

  @Test
  void getHomeEndpointExists() throws Exception {
    mockMvc.perform(
        get("/")
    ).andExpect(status().isOk());
  }

  @Test
  void postStartExists() throws Exception {
    mockMvc.perform(
        post("/start")
    ).andExpect(status().is3xxRedirection());
  }
}