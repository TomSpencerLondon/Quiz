package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Tag("integration")
@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class QuestionJpaRepositoryTest {

  @Autowired
  private QuestionJpaRepository questionJpaRepository;

  @Test
  void stores_and_retrieves_Questions() {
    final MultipleChoiceDto multipleChoiceDto = new MultipleChoiceDto();
    final QuestionDto questionDto = new QuestionDto();
    multipleChoiceDto.setCorrect("Q1A1");
    multipleChoiceDto.setAnswers(List.of("Q1A1", "Q1A2", "Q1A3", "Q1A4"));
    questionDto.setText("Q1");
    questionDto.setMultipleChoiceDto(multipleChoiceDto);

    final QuestionDto savedQuestion = questionJpaRepository.save(questionDto);

    assertThat(savedQuestion.getId())
        .isNotNull()
        .isGreaterThanOrEqualTo(0);

    Optional<QuestionDto> foundQuestion = questionJpaRepository
        .findByText("Q1");
    assertThat(foundQuestion)
        .isPresent();
  }
}
