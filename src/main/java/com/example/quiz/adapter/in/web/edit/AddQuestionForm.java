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
        this.choices = new ChoiceForm[]{new ChoiceForm(), new ChoiceForm(), new ChoiceForm(), new ChoiceForm()};
    }

    public AddQuestionForm(String text, ChoiceForm choice1, ChoiceForm choice2,
                           ChoiceForm choice3, ChoiceForm choice4, String choiceType) {
        this.text = text;
        this.choices = new ChoiceForm[4];
        this.choices[0] = choice1;
        this.choices[1] = choice2;
        this.choices[2] = choice3;
        this.choices[3] = choice4;
        this.choiceType = choiceType;
    }

    public String getText() {
        return text;
    }

    public ChoiceForm getChoice1() {
        return choices[0];
    }

    public ChoiceForm getChoice2() {
        return choices[1];
    }

    public ChoiceForm getChoice3() {
        return choices[2];
    }

    public ChoiceForm getChoice4() {
        return choices[3];
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChoice1(ChoiceForm choice1) {
        this.choices[0] = choice1;
    }

    public void setChoice2(ChoiceForm choice2) {
        this.choices[1] = choice2;
    }

    public void setChoice3(ChoiceForm choice3) {
        this.choices[2] = choice3;
    }

    public void setChoice4(ChoiceForm choice4) {
        this.choices[3] = choice4;
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
