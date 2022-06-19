package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.QuestionBuilder;
import com.example.quiz.hexagon.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Tag("integration")
public class QuestionRepositoryJpaAdapterTest extends TestContainerConfiguration {

    @Autowired
    QuestionJpaRepository questionJpaRepository;

    @Autowired
    QuestionTransformer questionTransformer;

    @BeforeEach
    public void clear() {
        questionJpaRepository.deleteAll();
    }

    @Test
    void newlySavedQuestionHasId() throws Exception {
        // given
        final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
                new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);
        Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        // when
        final Question savedQuestion = questionRepositoryJpaAdapter.save(question);

        // then
        assertThat(savedQuestion)
                .isNotNull();
        assertThat(savedQuestion.getId())
                .isNotNull();
    }

    @Test
    void finds_all_in_database() {
        // given
        final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
                new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);

        Question question1 = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();
        Question question2 = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        // when
        questionRepositoryJpaAdapter.save(question1);
        questionRepositoryJpaAdapter.save(question2);

        // then
        final List<Question> foundQuestions = questionRepositoryJpaAdapter.findAll();
        assertThat(foundQuestions)
                .hasSize(2);
    }
}
