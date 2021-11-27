package com.example.quiz.adapter.in.questionloader.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.adapter.in.web.QuestionRequest;
import com.example.quiz.domain.Question;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Profile("test")
@Tag("integration")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuizIntegrationTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private String url;

  @LocalServerPort
  private int port;

  @PostConstruct
  void init(){
    url = "http://localhost:" + port + "/quiz";
  }

  @Test
  void should_add_question_to_quiz() {
    final QuestionRequest questionRequest = new QuestionRequest("Q1",
        "Q1A1",
        List.of("Q1A1", "Q1A2", "Q1A3", "Q1A4"));

    HttpEntity<QuestionRequest> questionDtoHttpEntity = new HttpEntity<>(questionRequest);

    final Question addedQuestion = restTemplate.exchange(url, HttpMethod.POST,
        questionDtoHttpEntity, Question.class).getBody();

    assertThat(addedQuestion.getId())
        .isNotNull();
  }
}
