package com.example.quiz.application;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionServiceTest {

    @Test
    void savesQuestionFromQuestionForm() {
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
        Choice correctChoiceResult = new Choice("Answer 1");
        List<Choice> otherChoicesResult = List.of(
                new Choice("Answer 1"),
                new Choice("Answer 2"),
                new Choice("Answer 3"),
                new Choice("Answer 4"));
        SingleChoice singleChoiceResult = new SingleChoice(
                correctChoiceResult,
                otherChoicesResult);
        Question questionResult = new Question("Question 1",
                singleChoiceResult);
        questionResult.setId(0L);
        // Assert
        assertThat(questions)
                .containsExactly(questionResult);

    }

    @Test
    void savesMultipleChoiceQuestionForm() {
        // Arrange
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        QuestionService questionService = new QuestionService(inMemoryQuestionRepository);
        String questionText = "Question 1";
        ChoiceForm correctChoiceForm = new ChoiceForm("Answer 1", true);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", true);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);

        AddQuestionForm multipleChoiceQuestionForm = new AddQuestionForm(questionText, correctChoiceForm, choiceForm1, choiceForm2, choiceForm3, "multiple");

        // Act
        questionService.add(multipleChoiceQuestionForm);
        List<Question> questions = inMemoryQuestionRepository.findAll();
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));
        List<Choice> otherChoicesResult = List.of(
                new Choice("Answer 1"),
                new Choice("Answer 2"),
                new Choice("Answer 3"),
                new Choice("Answer 4"));
        MultipleChoice multipleChoice = new MultipleChoice(
                correctChoices,
                otherChoicesResult);
        Question questionResult = new Question("Question 1",
                multipleChoice);
        questionResult.setId(0L);

        // Assert
        assertThat(questions.get(0).isSingleChoice())
                .isFalse();
    }
}