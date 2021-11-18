package com.example.quiz.adapter.in.questionloader.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.adapter.port.repository.jpa.QuestionDto;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Profile("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddQuestionTest {
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
    HttpEntity<Question> questionDtoHttpEntity = new HttpEntity<>(
        new Question("Q1", new MultipleChoice(new Answer("Q1A1"),
            List.of(new Answer("Q1A1"),
            new Answer("Q1A2"))
        ))
    );

    final Question addedQuestion = restTemplate.exchange(url, HttpMethod.POST,
        questionDtoHttpEntity, Question.class).getBody();


    assertThat(addedQuestion.getId())
        .isNotNull();
  }
}
