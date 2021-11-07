package com.example.quiz.adapter.port.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionRepositoryTest {

  @Autowired
  private QuestionRepository questionRepository;

  @Test
  void stores_and_retrieves_Questions() {
    final QuestionDto questionDto = new QuestionDto();
    final MultipleChoiceDto multipleChoiceDto = new MultipleChoiceDto();
    multipleChoiceDto.setCorrect("Q1A1");
    multipleChoiceDto.setAnswers(List.of("Q1A1", "Q1A2", "Q1A3", "Q1A4"));
    questionDto.setText("Q1");
    questionDto.setMultipleChoiceDto(multipleChoiceDto);

    final QuestionDto savedQuestion = questionRepository.save(questionDto);

    assertThat(savedQuestion.getId()).isNotNull();
  }
}
