package com.example.quiz.application;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionServiceTest {

    @Test
    void givenSingleChoiceQuestionFormThenSavesSingleChoiceQuestionInRepository() {
        // Arrange
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(inMemoryQuestionRepository);
        String questionText = "Question 1";
        ChoiceForm correctChoiceForm = new ChoiceForm("Answer 1", true);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", false);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);

        AddQuestionForm singleChoiceQuestionForm = new AddQuestionForm(questionText, correctChoiceForm, choiceForm1, choiceForm2, choiceForm3, "single");

        // Act
        questionService.add(singleChoiceQuestionForm);
        List<Question> questions = inMemoryQuestionRepository.findAll();
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", false),
                new Choice("Answer 3", false),
                new Choice("Answer 4", false));
        ChoiceType singleChoiceResult = new SingleChoice(choices);
        Question questionResult = new Question("Question 1",
                singleChoiceResult);
        questionResult.setId(QuestionId.of(1L));

        // Assert
        assertThat(questions)
                .containsExactly(questionResult);
    }

    @Test
    void givenMultipleChoiceQuestionFormThenSavesMultipleChoiceQuestionInRepository() {
        // Arrange
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(inMemoryQuestionRepository);
        String questionText = "Question 1";
        ChoiceForm correct1 = new ChoiceForm("Answer 1", true);
        ChoiceForm correct2 = new ChoiceForm("Answer 2", true);
        ChoiceForm choice3 = new ChoiceForm("Answer 3", false);
        ChoiceForm choice4 = new ChoiceForm("Answer 4", false);

        AddQuestionForm multipleChoiceQuestionForm = new AddQuestionForm(questionText,
                correct1,
                correct2,
                choice3,
                choice4,
                "multiple");

        // Act
        questionService.add(multipleChoiceQuestionForm);

        // Assert
        Question actualQuestion = inMemoryQuestionRepository.findAll().get(0);
        assertThat(actualQuestion.isSingleChoice())
                .isFalse();
        assertThat(actualQuestion.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", true)))
                .isTrue();
        assertThat(actualQuestion.text())
                .isEqualTo(questionText);
        assertThat(actualQuestion.choices())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(
                        new Choice("Answer 1", true),
                        new Choice("Answer 2", true),
                        new Choice("Answer 3", false),
                        new Choice("Answer 4", false)));
    }
}