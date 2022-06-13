package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class QuestionDboBuilder {
    private String text;
    AtomicLong choiceId = new AtomicLong(1);
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
                    choiceDbo.setId(choiceId.getAndIncrement());
                    return choiceDbo;
                }).collect(Collectors.toList());
        this.choiceType = question.isSingleChoice() ? ChoiceType.SINGLE : ChoiceType.MULTIPLE;
        return this;
    }


    public QuestionDbo build() {
        QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setChoices(choiceDbos);
        questionDbo.setText(text);
        questionDbo.setId(question.getId().id());
        questionDbo.setChoiceType(choiceType);
        return questionDbo;
    }
}
