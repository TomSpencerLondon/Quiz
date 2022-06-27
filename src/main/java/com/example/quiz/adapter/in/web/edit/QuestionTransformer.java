package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.hexagon.domain.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("webEditQuestionTransformer")
public class QuestionTransformer {
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
