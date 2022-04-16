package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
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
public class QuestionRepositoryJpaAdapterTest implements TestContainerConfiguration {

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
        final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
                new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);

        Question question = new Question("Q1",
                new SingleChoice(
                        List.of(new Choice("Q1A1", true),
                                new Choice("Q1A2", false),
                                new Choice("Q1A3", false),
                                new Choice("Q1A4", false))));

        final Question savedQuestion = questionRepositoryJpaAdapter.save(question);

        assertThat(savedQuestion)
                .isNotNull();

        assertThat(savedQuestion.getId())
                .isNotNull();
    }

    @Test
    void finds_all_in_database() {
        final QuestionRepositoryJpaAdapter questionRepositoryJpaAdapter =
                new QuestionRepositoryJpaAdapter(questionJpaRepository, questionTransformer);

        Question question1 = new Question("Q1",
                new SingleChoice(List.of(new Choice("Q1A1", true),
                        new Choice("Q1A2"),
                        new Choice("Q1A3"),
                        new Choice("Q1A4"))));

        Question question2 = new Question("Q2",
                new SingleChoice(List.of(new Choice("Q2A1", true),
                        new Choice("Q2A2"),
                        new Choice("Q2A3"),
                        new Choice("Q2A4"))));
        questionRepositoryJpaAdapter.save(question1);
        questionRepositoryJpaAdapter.save(question2);

        final List<Question> foundQuestions = questionRepositoryJpaAdapter.findAll();

        assertThat(foundQuestions)
                .hasSize(2);
    }
}
