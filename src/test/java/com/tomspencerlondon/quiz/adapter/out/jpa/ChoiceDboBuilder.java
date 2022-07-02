package com.tomspencerlondon.quiz.adapter.out.jpa;

public class ChoiceDboBuilder {
    private Long id;
    private String text;
    private boolean isCorrect;

    public ChoiceDboBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ChoiceDboBuilder withText(String text) {
        this.text = text;
        return this;
    }
    public ChoiceDboBuilder withCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
        return this;
    }

    public ChoiceDbo build() {
        ChoiceDbo choiceDbo = new ChoiceDbo();
        choiceDbo.setId(id);
        choiceDbo.setChoiceText(text);
        choiceDbo.setCorrect(isCorrect);

        return choiceDbo;
    }
}
