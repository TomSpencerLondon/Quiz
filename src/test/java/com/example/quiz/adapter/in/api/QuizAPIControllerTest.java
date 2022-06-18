package com.example.quiz.adapter.in.api;

import com.example.quiz.hexagon.application.CreateQuestionService;
import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.SingleChoice;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizAPIController.class)
@Tag("integration")
class QuizAPIControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    CreateQuestionService createQuestionService;

    @Test
    void shouldExposeQuizResourceWithoutAuthentication() throws Exception {
        when(createQuestionService.findAll())
                .thenReturn(List.of(new Question("Q1",
                        new SingleChoice(
                                List.of(new Choice("Q1A1", true),
                                        new Choice("Q1A2", false),
                                        new Choice("Q1A3", false),
                                        new Choice("Q1A4", false))))));

        this.mockMvc.perform(get("/api/questions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)))
                    .andExpect(jsonPath("$[0].text", is("Q1")));
    }

    @Test
    void shouldAllowQuizCreationForAnonymousUser() throws Exception {
        this.mockMvc.perform(post("/api/questions")
                .content(
                        """
                                    {
                                      "text": "How do you create a form",
                                      "answerDbo": "Use html knowledge",
                                      "choice1": "Give up!",
                                      "choice2": "Go to bed!",
                                      "choice3": "Go for a walk",
                                      "choice4": "Use html knowledge"
                                    }
                                """
                )
        ).andExpect(status().isCreated());
    }
}