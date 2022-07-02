package com.tomspencerlondon.quiz.adapter.in.api;

import com.tomspencerlondon.CorrectAnswer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Stream;

@CorrectAnswer
public class AddQuestionRequest {

    @NotBlank
    private String text;
    @NotBlank
    private String choiceType;
    @Valid
    private ChoiceRequest[] choices;

    public AddQuestionRequest() {
        this.choices = new ChoiceRequest[]{};
    }

    public AddQuestionRequest(int count) {
        ChoiceRequest[] choiceRequests = new ChoiceRequest[count];
        for (int i = 0; i < count; i++) {
            choiceRequests[i] = new ChoiceRequest();
        }

        this.choices = choiceRequests;
    }

    public AddQuestionRequest(String text, String choiceType, ChoiceRequest[] choiceForms) {
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

    public ChoiceRequest[] getChoices() {
        return choices;
    }

    public void setChoices(ChoiceRequest[] choices) {
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
                     .map(ChoiceRequest::getChoice)
                     .toList();
    }

    public List<String> transformToCorrectChoices() {
        return correctChoices();
    }

    private List<String> correctChoices() {
        return Stream.of(choices)
                     .filter(ChoiceRequest::isCorrectAnswer)
                     .map(ChoiceRequest::getChoice)
                     .toList();
    }
}
