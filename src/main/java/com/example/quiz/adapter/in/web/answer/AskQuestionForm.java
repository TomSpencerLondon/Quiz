package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Question;

import java.util.List;

public class AskQuestionForm {
    private String question;
    private List<ChoiceSelection> choices;
    // make this a list of integers to reuse the class for single and multiple choice
    private long[] selectedChoices;

    public static AskQuestionForm from(Question question) {
        final AskQuestionForm askQuestionForm = new AskQuestionForm();
        askQuestionForm.setQuestion(question.text());
        final List<ChoiceSelection> choices = question
                .choices()
                .stream()
                .map(c -> new ChoiceSelection(c.getId().id(), c.text()))
                .toList();
        askQuestionForm.setChoices(choices);
        long[] choiceIndexes = choices.stream().map(ChoiceSelection::getChoiceId).mapToLong(Long::longValue).toArray();
        askQuestionForm.setSelectedChoices(choiceIndexes);

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

    public long[] getSelectedChoices() {
        return selectedChoices;
    }

    public void setSelectedChoices(long... selectedChoices) {
        this.selectedChoices = selectedChoices;
    }
}
