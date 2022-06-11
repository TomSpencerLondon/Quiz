package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.domain.*;

import java.util.Arrays;
import java.util.List;

public class QuestionFactory {
    public Question transform(AddQuestionForm addQuestionForm) {
        List<ChoiceForm> choiceForms = Arrays.stream(addQuestionForm.getChoices())
                                             .toList();
        List<Choice> choices = extractChoicesFrom(choiceForms);
        return questionFrom(addQuestionForm, choices);
    }

    private Question questionFrom(AddQuestionForm addQuestionForm, List<Choice> choices) {
        if (addQuestionForm.getChoiceType().equals("single")) {
            ChoiceType singleChoice = new SingleChoice(choices);
            return new Question(addQuestionForm.getText(), singleChoice);
        } else {
            MultipleChoice multipleChoice = new MultipleChoice(choices);
            return new Question(addQuestionForm.getText(), multipleChoice);
        }
    }

    private List<Choice> extractChoicesFrom(List<ChoiceForm> choices) {
        return choices.stream().map(c -> new Choice(c.getChoice(), c.isCorrectAnswer())).toList();
    }
}
