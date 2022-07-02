package com.tomspencerlondon.quiz.adapter.out.jpa;

import com.tngtech.archunit.base.Optional;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDboBuilder {
    private String text;
    private List<ChoiceDbo> choiceDbos;
    private ChoiceType choiceType;
    private Question question;

    public QuestionDboBuilder from(Question question) {
        this.question = question;
        this.text = question.text();
        this.choiceDbos = question
                .choices()
                .stream()
                .map(c -> {
                    ChoiceDbo choiceDbo = new ChoiceDbo();
                    choiceDbo.setChoiceText(c.text());
                    choiceDbo.setCorrect(c.isCorrect());
                    choiceDbo.setId(Optional.ofNullable(c.getId()).map(i -> i.id()).orElse(null));
                    return choiceDbo;
                }).collect(Collectors.toList());
        this.choiceType = question.isSingleChoice() ? ChoiceType.SINGLE : ChoiceType.MULTIPLE;
        return this;
    }


    public QuestionDbo build() {
        QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setChoices(choiceDbos);
        questionDbo.setText(text);
        questionDbo.setId(Optional.ofNullable(question.getId()).map(QuestionId::id).orElse(null));
        questionDbo.setChoiceType(choiceType);
        return questionDbo;
    }
}
