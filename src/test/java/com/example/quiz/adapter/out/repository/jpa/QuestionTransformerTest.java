package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.factories.MultipleChoiceQuestionTestFactory;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTransformerTest {

    final QuestionTransformer questionTransformer = new QuestionTransformer();

    @Test
    void questionDboToSingleChoiceQuestion() {
        // Given
        final Question expected = SingleChoiceQuestionTestFactory.create(
                "Question 1",
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
        );
        expected.setId(1L);

        final QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText("Question 1");
        ChoiceDbo choiceDbo1 = new ChoiceDbo();
        choiceDbo1.setChoiceText("Answer 1");
        choiceDbo1.setCorrect(true);
        ChoiceDbo choiceDbo2 = new ChoiceDbo();
        choiceDbo2.setChoiceText("Answer 2");
        choiceDbo2.setCorrect(false);
        ChoiceDbo choiceDbo3 = new ChoiceDbo();
        choiceDbo3.setChoiceText("Answer 3");
        choiceDbo3.setCorrect(false);
        ChoiceDbo choiceDbo4 = new ChoiceDbo();
        choiceDbo4.setChoiceText("Answer 4");
        choiceDbo4.setCorrect(false);
        questionDbo.setChoices(List.of(choiceDbo1, choiceDbo2, choiceDbo3, choiceDbo4));
        questionDbo.setId(1L);
        // When
        final Question question = questionTransformer.toQuestion(questionDbo);

        // Then
        assertThat(question)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void singleChoiceQuestionToQuestionDbo() {
        // Given
        final Question question = SingleChoiceQuestionTestFactory.create(
                "Question 1",
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
        );

        final QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText("Question 1");
        ChoiceDbo choiceDbo1 = new ChoiceDbo();
        choiceDbo1.setChoiceText("Answer 1");
        choiceDbo1.setCorrect(true);
        ChoiceDbo choiceDbo2 = new ChoiceDbo();
        choiceDbo2.setChoiceText("Answer 2");
        choiceDbo2.setCorrect(false);
        ChoiceDbo choiceDbo3 = new ChoiceDbo();
        choiceDbo3.setChoiceText("Answer 3");
        choiceDbo3.setCorrect(false);
        ChoiceDbo choiceDbo4 = new ChoiceDbo();
        choiceDbo4.setChoiceText("Answer 4");
        choiceDbo4.setCorrect(false);
        questionDbo.setChoices(List.of(choiceDbo1, choiceDbo2, choiceDbo3, choiceDbo4));

        // When
        final QuestionDbo result = questionTransformer.toQuestionDbo(question);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(questionDbo);

    }

    @Test
    void multipleChoiceQuestionToQuestionDbo() {
        final List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", true),
                new Choice("Answer 3"), new Choice("Answer 4"));
        MultipleChoice multipleChoice = new MultipleChoice(choices);
        Question multipleChoiceQuestion = new Question("Question 1", multipleChoice);

        final QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText("Question 1");
        ChoiceDbo choiceDbo1 = new ChoiceDbo();
        choiceDbo1.setChoiceText("Answer 1");
        choiceDbo1.setCorrect(true);
        ChoiceDbo choiceDbo2 = new ChoiceDbo();
        choiceDbo2.setChoiceText("Answer 2");
        choiceDbo2.setCorrect(true);
        ChoiceDbo choiceDbo3 = new ChoiceDbo();
        choiceDbo3.setChoiceText("Answer 3");
        choiceDbo3.setCorrect(false);
        ChoiceDbo choiceDbo4 = new ChoiceDbo();
        choiceDbo4.setChoiceText("Answer 4");
        choiceDbo4.setCorrect(false);
        questionDbo.setChoices(List.of(choiceDbo1, choiceDbo2, choiceDbo3, choiceDbo4));


        // When
        final QuestionDbo result = questionTransformer.toQuestionDbo(multipleChoiceQuestion);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(questionDbo);
    }

    @Test
    void questionDboToMultipleChoiceQuestion() {
        // Given
        final Question question = MultipleChoiceQuestionTestFactory.multipleChoiceQuestion();
        question.setId(1L);

        final QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText("Question 1");
        ChoiceDbo choiceDbo1 = new ChoiceDbo();
        choiceDbo1.setChoiceText("Answer 1");
        choiceDbo1.setCorrect(true);
        ChoiceDbo choiceDbo2 = new ChoiceDbo();
        choiceDbo2.setChoiceText("Answer 2");
        choiceDbo2.setCorrect(true);
        ChoiceDbo choiceDbo3 = new ChoiceDbo();
        choiceDbo3.setChoiceText("Answer 3");
        choiceDbo3.setCorrect(false);
        ChoiceDbo choiceDbo4 = new ChoiceDbo();
        choiceDbo4.setChoiceText("Answer 4");
        choiceDbo4.setCorrect(false);
        questionDbo.setChoices(List.of(choiceDbo1, choiceDbo2, choiceDbo3, choiceDbo4));
        questionDbo.setId(1L);

        // When
        final Question result = questionTransformer.toQuestion(questionDbo);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(question);

        assertThat(result.isSingleChoice())
                .isFalse();
    }
}