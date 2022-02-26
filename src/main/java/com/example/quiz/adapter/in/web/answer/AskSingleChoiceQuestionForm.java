package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;

import java.util.List;

public class AskSingleChoiceQuestionForm {
    private String question;
    private List<String> choices;
    private String selectedChoice;
    private int index;

    public static AskSingleChoiceQuestionForm from(Question question) {
        final AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = new AskSingleChoiceQuestionForm();
        askSingleChoiceQuestionForm.setQuestion(question.text());
        final List<String> choices = question.choices()
                                             .stream()
                                             .map(Choice::text)
                                             .toList();
        askSingleChoiceQuestionForm.setChoices(choices);

        return askSingleChoiceQuestionForm;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceSelection> getChoices() {
        return List.of(new ChoiceSelection(0, choices.get(0)), new ChoiceSelection(1, choices.get(1)));
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
}
