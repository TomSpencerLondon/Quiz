package com.example.quiz.adapter.in.web.edit;

import com.example.CorrectAnswer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Stream;

@CorrectAnswer
public class AddQuestionForm {

    @NotBlank
    private String text;
    @NotBlank
    private String choiceType;
    private @Valid ChoiceForm[] choices;

    public AddQuestionForm() {
        this.choices = new ChoiceForm[]{};
    }

    public AddQuestionForm(String text, String choiceType, ChoiceForm... choiceForms) {
        this.text = text;
        this.choices = choiceForms;
        this.choiceType = choiceType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChoiceForm[] getChoices() {
        return choices;
    }

    public void setChoices(ChoiceForm[] choices) {
        this.choices = choices;
    }

    public String getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(String choiceType) {
        this.choiceType = choiceType;
    }

    public List<String> transformToChoices() {
        return Stream.of(choices)
                     .map(ChoiceForm::getChoice)
                     .toList();
    }

    public List<String> transformToCorrectChoices() {
        List<String> correctChoices = correctChoices();
        checkForTooManyCorrectChoicesInSingleChoice(correctChoices);
        checkForTooFewCorrectChoicesInMultipleChoice(correctChoices);
        return correctChoices;
    }

    private void checkForTooFewCorrectChoicesInMultipleChoice(List<String> correctChoices) {
        if (correctChoices.size() < 2 && choiceType.equals("multiple")) {
            throw new TooFewCorrectChoicesSelected(correctChoices);
        }
    }

    private void checkForTooManyCorrectChoicesInSingleChoice(List<String> correctChoices) {
        if (correctChoices.size() > 1 && choiceType.equals("single")) {
            throw new TooManyCorrectChoicesSelected(correctChoices);
        }
    }

    private List<String> correctChoices() {
        return Stream.of(choices)
                     .filter(ChoiceForm::isCorrectAnswer)
                     .map(ChoiceForm::getChoice)
                     .toList();
    }
}
