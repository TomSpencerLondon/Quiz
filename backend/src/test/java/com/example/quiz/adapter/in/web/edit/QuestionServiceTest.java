package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.domain.Question;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionServiceTest {
    @Test
    void transformAddQuestionFormWithSingleQuestionToSingleChoiceQuestion() {
        String questionText = "Question 1";
        ChoiceForm correctChoiceForm = new ChoiceForm("Answer 1", true);
        ChoiceForm choiceForm1 = new ChoiceForm("Answer 2", false);
        ChoiceForm choiceForm2 = new ChoiceForm("Answer 3", false);
        ChoiceForm choiceForm3 = new ChoiceForm("Answer 4", false);
        AddQuestionForm singleChoiceQuestionForm = new AddQuestionForm(questionText, "single", correctChoiceForm, choiceForm1, choiceForm2, choiceForm3);
        QuestionService questionService = new QuestionService();

        Question question = questionService.transform(singleChoiceQuestionForm);

        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void transformAddQuestionFormWithMultipleChoiceQuestionToMultipleChoiceQuestion() {
        String questionText = "Question 1";
        ChoiceForm correct1 = new ChoiceForm("Answer 1", true);
        ChoiceForm correct2 = new ChoiceForm("Answer 2", true);
        ChoiceForm choice3 = new ChoiceForm("Answer 3", false);
        ChoiceForm choice4 = new ChoiceForm("Answer 4", false);

        AddQuestionForm addQuestionForm = new AddQuestionForm(questionText,
                "multiple", correct1,
                correct2,
                choice3,
                choice4
        );
        QuestionService questionService = new QuestionService();

        Question question = questionService.transform(addQuestionForm);

        assertThat(question.isSingleChoice())
                .isFalse();
    }
}
