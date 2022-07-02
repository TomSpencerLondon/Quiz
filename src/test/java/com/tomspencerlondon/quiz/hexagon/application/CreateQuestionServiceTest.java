package com.tomspencerlondon.quiz.hexagon.application;

import com.tomspencerlondon.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.domain.ChoiceBuilder;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateQuestionServiceTest {

    @Test
    void givenSingleChoiceQuestionFormThenSavesSingleChoiceQuestionInRepository() {
        // Arrange
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        CreateQuestionService createQuestionService = new CreateQuestionService(inMemoryQuestionRepository);

        Question question = new QuestionBuilder().withQuestionId(1L).withDefaultSingleChoice().build();
        // Act
        createQuestionService.add(question);
        List<Question> questions = inMemoryQuestionRepository.findAll();

        Question expected = new QuestionBuilder().withQuestionId(1L).withDefaultSingleChoice().build();

        // Assert
        assertThat(questions)
                .containsExactly(expected);
    }

    @Test
    void givenMultipleChoiceQuestionFormThenSavesMultipleChoiceQuestionInRepository() {
        // Arrange
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        CreateQuestionService createQuestionService = new CreateQuestionService(inMemoryQuestionRepository);

        Question question = new QuestionBuilder().withQuestionId(1L).withDefaultMultipleChoice().build();

        // Act
        createQuestionService.add(question);

        // Assert
        Question actualQuestion = inMemoryQuestionRepository.findAll().get(0);
        assertThat(actualQuestion.isSingleChoice())
                .isFalse();
        Choice[] choices = new ChoiceBuilder().withCorrectChoice().withCorrectChoice().asArray();
        assertThat(actualQuestion.isCorrectAnswer(choices))
                .isTrue();
        assertThat(actualQuestion.text())
                .isEqualTo(question.text());
        List<Choice> expected = new ChoiceBuilder().withCorrectChoice().withCorrectChoice().withIncorrectChoice().asList();
        assertThat(actualQuestion.choices())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}