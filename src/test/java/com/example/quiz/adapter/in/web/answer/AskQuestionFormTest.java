package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AskQuestionFormTest {
    @Test
    void convertsQuestionToAskSingleChoiceForm() {
        Question singleChoiceQuestion = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2"))));

        AskQuestionForm form = AskQuestionForm.from(singleChoiceQuestion);

        assertThat(form.getQuestion())
                .isEqualTo("Question 1");
        assertThat(form.getChoices())
                .containsExactly(new ChoiceSelection(0, "Answer 1"), new ChoiceSelection(1, "Answer 2"));
    }
}