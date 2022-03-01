package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.in.web.answer.QuizController;
import com.example.quiz.domain.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@WebMvcTest(QuizController.class)
@Import(TestQuizConfiguration.class)
@WithMockUser(username = "tom")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class QuizControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuizSession mockQuizSession;

    @Test
    void questionEndpointExists() throws Exception {
        Question question = getQuestion();
        when(mockQuizSession.question())
                .thenReturn(question);

        mockMvc.perform(
                get("/quiz")
        ).andExpect(status().isOk());
    }

    @Test
    void resultEndPointExists() throws Exception {
        Question question = getQuestion();
        when(mockQuizSession.grade())
                .thenReturn(
                        new Grade(
                                List.of(new Response(question, new Choice("Answer 1"))),
                                1,
                                0
                        ));
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
    void shouldRedirectToResultIfQuizIsFinished() throws Exception {
        when(mockQuizSession.isFinished())
                .thenReturn(true);

        mockMvc.perform(get("/quiz"))
               .andExpect(status().is3xxRedirection())
               .andExpect(header().stringValues("Location", "/result"));
    }

    @Test
    void shouldReturnSingleViewForSingleChoiceQuestion() throws Exception {
        Question question = getQuestion();
        when(mockQuizSession.question())
                .thenReturn(question);

        mockMvc.perform(get("/quiz"))
               .andExpect(status().isOk())
               .andExpect(view().name("single-choice"))
               .andExpect(model().attributeExists("askQuestionForm"));
    }

    @NotNull
    private Question getQuestion() {
        return new Question("Question 1",
                new SingleChoice(new Choice("Answer 1"),
                        List.of(new Choice("Answer 1"),
                                new Choice("Answer 2"),
                                new Choice("Answer 3"),
                                new Choice("Answer 4")
                        )));
    }

    @Test
    void shouldReturnMultipleChoiceViewForMultipleChoiceQuestion() throws Exception {
        when(mockQuizSession.question())
                .thenReturn(new Question("Question 1",
                        new MultipleChoice(List.of(new Choice("Answer 1"), new Choice("Answer 2")),
                                List.of(
                                        new Choice("Answer 1"),
                                        new Choice("Answer 2"),
                                        new Choice("Answer 3"),
                                        new Choice("Answer 2")
                                ))));

        mockMvc.perform(get("/quiz"))
               .andExpect(status().isOk())
               .andExpect(view().name("multiple-choice"))
               .andExpect(model().attributeExists("askQuestionForm"));
    }
}