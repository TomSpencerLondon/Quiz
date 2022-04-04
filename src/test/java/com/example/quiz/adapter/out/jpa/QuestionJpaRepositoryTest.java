package com.example.quiz.adapter.out.jpa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
public class QuestionJpaRepositoryTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("test")
            .withUsername("duke")
            .withPassword("s3cret");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private QuestionJpaRepository questionJpaRepository;

    @Test
    void stores_and_retrieves_Questions() {
        QuestionDbo question = new QuestionDbo();
        question.setText("Q1");

        QuestionDbo savedQuestion = questionJpaRepository.save(question);

        assertThat(savedQuestion.getId())
                .isNotNull()
                .isGreaterThanOrEqualTo(0);

        Optional<QuestionDbo> foundQuestion = questionJpaRepository
                .findByText("Q1");
        assertThat(foundQuestion)
                .isPresent();
    }
}
