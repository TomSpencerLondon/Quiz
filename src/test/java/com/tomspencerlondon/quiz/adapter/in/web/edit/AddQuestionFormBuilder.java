package com.tomspencerlondon.quiz.adapter.in.web.edit;

import com.tomspencerlondon.quiz.hexagon.domain.Question;

public class AddQuestionFormBuilder {

    private Question question;

    public AddQuestionFormBuilder withQuestion(Question question) {
        this.question = question;
        return this;
    }

    public AddQuestionForm build() {
        AddQuestionForm addQuestionForm = new AddQuestionForm();
        ChoiceForm[] choiceForms = question.choices()
                                           .stream()
                                           .map(c -> new ChoiceForm(c.text(), c.isCorrect()))
                                           .toArray(ChoiceForm[]::new);
        addQuestionForm.setText(question.text());
        addQuestionForm.setChoices(choiceForms);
        addQuestionForm.setChoiceType(question.isSingleChoice() ? "single" : "multiple");
        return addQuestionForm;
    }
}
