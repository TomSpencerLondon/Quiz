package com.example.quiz.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.port.QuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import com.example.quiz.domain.quiz.QuizSession;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Tag("integration")
@WebMvcTest(QuizController.class)
@Import(TestQuizConfiguration.class)
class QuizControllerWebMvcTest {

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
        .param("answer", "Q1A1")
        .param("choice1", "Q1A1")
        .param("choice2", "Q1A2")
        .param("choice3", "Q1A3")
        .param("choice4", "Q1A4")
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
}