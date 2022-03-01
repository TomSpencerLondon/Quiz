package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AskQuestionForm {
    private String question;
    private List<ChoiceSelection> choices;
    // make this a list of integers to reuse the class for single and multiple choice
    private int[] selectedChoices;

    public static AskQuestionForm from(Question question) {
        final AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(question.text());
        AtomicInteger index = new AtomicInteger(0);
        final List<ChoiceSelection> choices = question
                .choices()
                .stream()
                .map(c -> new ChoiceSelection(index.getAndIncrement(), c.text()))
                .toList();
        askQuestionForm.setChoices(choices);
        int[] choiceIndexes = choices.stream().map(ChoiceSelection::getIndex).mapToInt(Integer::intValue).toArray();
        askQuestionForm.setSelectedChoice(choiceIndexes);

        return askQuestionForm;
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

    public int[] getSelectedChoices() {
        return selectedChoices;
    }

    public void setSelectedChoice(int... selectedChoices) {
        this.selectedChoices = selectedChoices;
    }
}
