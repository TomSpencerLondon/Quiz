package com.example.quiz.adapter.in.web.answer;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@WebMvcTest(QuizController.class)
@Import(TestSessionConfiguration.class)
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

    @Test
    void shouldReturnSingleViewForSingleChoiceQuestion() throws Exception {
        mockMvc.perform(post("/start"));
        mockMvc.perform(get("/quiz"))
               .andExpect(status().isOk());
    }

}