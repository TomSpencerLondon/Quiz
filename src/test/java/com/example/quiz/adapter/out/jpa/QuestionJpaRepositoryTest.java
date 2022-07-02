package com.example.quiz.adapter.out.jpa;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
public class QuestionJpaRepositoryTest extends TestContainerConfiguration {
    @Autowired
    private QuestionJpaRepository questionJpaRepository;

    @Test
    void stores_and_retrieves_Questions() {
        // given
        Question question = new QuestionBuilder()
                .withDefaultSingleChoiceWithoutIds()
                .build();
        QuestionDbo questionDbo = new QuestionDboBuilder()
                .from(question)
                .build();

        // when
        QuestionDbo savedQuestion = questionJpaRepository.save(questionDbo);

        // then
        assertThat(savedQuestion.getId())
                .isNotNull()
                .isGreaterThanOrEqualTo(0);
        Optional<QuestionDbo> foundQuestion = questionJpaRepository
                .findByText("Question 1");
        assertThat(foundQuestion)
                .isPresent();
        assertThat(foundQuestion.get().getChoiceType())
                .isEqualTo(ChoiceType.SINGLE);
        List<ChoiceDbo> choices = foundQuestion.get().getChoices();
        assertThat(choices.get(0).getChoiceText())
                .isEqualTo("Answer 1");
    }
}
