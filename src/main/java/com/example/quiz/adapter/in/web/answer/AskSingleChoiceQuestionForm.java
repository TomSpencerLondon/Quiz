package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AskSingleChoiceQuestionForm {
    private String question;
    private List<ChoiceSelection> choices;
    private String selectedChoice;

    public static AskSingleChoiceQuestionForm from(Question question) {
        final AskSingleChoiceQuestionForm askSingleChoiceQuestionForm = new AskSingleChoiceQuestionForm();
        askSingleChoiceQuestionForm.setQuestion(question.text());
        AtomicInteger index = new AtomicInteger(0);
        final List<ChoiceSelection> choices = question
                .choices()
                .stream()
                .map(c -> new ChoiceSelection(index.getAndIncrement(), c.text()))
                .toList();
        askSingleChoiceQuestionForm.setChoices(choices);

        return askSingleChoiceQuestionForm;
    }

    public List<ChoiceSelection> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceSelection> choices) {
        this.choices = choices;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
}
