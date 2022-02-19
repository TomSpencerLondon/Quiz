package com.example.quiz.adapter.in.web;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Stream;

public class AddQuestionForm {

    @NotBlank
    private String text;
    @Valid
    private ChoiceForm choice1;
    @Valid
    private ChoiceForm choice2;
    @Valid
    private ChoiceForm choice3;
    @Valid
    private ChoiceForm choice4;
    @NotBlank
    private String choiceType;

    public AddQuestionForm() {
    }

    public AddQuestionForm(String text, ChoiceForm choice1, ChoiceForm choice2,
                           ChoiceForm choice3, ChoiceForm choice4, String choiceType) {
        this.text = text;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.choiceType = choiceType;
    }

    public String getText() {
        return text;
    }

    public ChoiceForm getChoice1() {
        return choice1;
    }

    public ChoiceForm getChoice2() {
        return choice2;
    }

    public ChoiceForm getChoice3() {
        return choice3;
    }

    public ChoiceForm getChoice4() {
        return choice4;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChoice1(ChoiceForm choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(ChoiceForm choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(ChoiceForm choice3) {
        this.choice3 = choice3;
    }

    public void setChoice4(ChoiceForm choice4) {
        this.choice4 = choice4;
    }

    public String getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(String choiceType) {
        this.choiceType = choiceType;
    }

    public List<String> transformToChoices() {
        return Stream.of(choice1, choice2, choice3, choice4)
                     .map(ChoiceForm::getChoice)
                     .toList();
    }
}
