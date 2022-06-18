package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.domain.QuestionBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionFactoryTest {
    @Test
    void transformAddQuestionFormWithSingleQuestionToSingleChoiceQuestion() {
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(new QuestionBuilder().withDefaultSingleChoice().build())
                .build();
        QuestionFactory questionFactory = new QuestionFactory();

        Question question = questionFactory.transform(addQuestionForm);

        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void transformAddQuestionFormWithMultipleChoiceQuestionToMultipleChoiceQuestion() {
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(new QuestionBuilder().withDefaultMultipleChoice().build())
                .build();
        QuestionFactory questionFactory = new QuestionFactory();

        Question question = questionFactory.transform(addQuestionForm);

        assertThat(question.isSingleChoice())
                .isFalse();
    }
}
