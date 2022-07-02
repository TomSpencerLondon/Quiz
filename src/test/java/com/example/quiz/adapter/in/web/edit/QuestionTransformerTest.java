package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTransformerTest {
    @Test
    void transformAddQuestionFormWithSingleQuestionToSingleChoiceQuestion() {
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(new QuestionBuilder().withDefaultSingleChoice().build())
                .build();
        QuestionTransformer questionTransformer = new QuestionTransformer();

        Question question = questionTransformer.transform(addQuestionForm);

        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void transformAddQuestionFormWithMultipleChoiceQuestionToMultipleChoiceQuestion() {
        AddQuestionForm addQuestionForm = new AddQuestionFormBuilder()
                .withQuestion(new QuestionBuilder().withDefaultMultipleChoice().build())
                .build();
        QuestionTransformer questionTransformer = new QuestionTransformer();

        Question question = questionTransformer.transform(addQuestionForm);

        assertThat(question.isSingleChoice())
                .isFalse();
    }
}
