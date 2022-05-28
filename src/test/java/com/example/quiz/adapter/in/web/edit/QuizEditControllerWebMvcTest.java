package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.adapter.out.web.initialChoiceCount.ChoiceCountConfig;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@WebMvcTest(QuizEditController.class)
@Import(TestQuizEditConfiguration.class)
public class QuizEditControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    @MockBean
    ChoiceCountConfig choiceCountConfig;

    @Test
    @WithMockUser(username = "tom")
    void addQuestionRedirects() throws Exception {
        mockMvc.perform(post("/add-question")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "Q1")
                .param("choices[0].choice", "Q1A1")
                .param("choices[0].correctAnswer", "true")
                .param("choices[1].choice", "Q1A2")
                .param("choices[1].correctAnswer", "false")
                .param("choices[2].choice", "Q1A3")
                .param("choices[2].correctAnswer", "false")
                .param("choices[3].choice", "Q1A4")
                .param("choices[3].correctAnswer", "false")
                .param("choiceType", "single")
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
    void shouldIncrementFormIndexByOne() throws Exception {
        mockMvc.perform(
                get("/add-choice?index=1")
        ).andExpect(status().isOk());
    }

    @Test
    void postCreateQuizExists() throws Exception {
        mockMvc.perform(
                post("/create-quiz")
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void getQuizExists() throws Exception {
        mockMvc.perform(
                get("/quiz?quizId=QUIZ_ID")
        ).andExpect(status().isOk());
    }

    @Test
    void makerShowsListOfQuestionsToSelectForQuiz() throws Exception {
        mockMvc.perform(
                get("/maker")
        ).andExpect(status().isOk());
    }
}
