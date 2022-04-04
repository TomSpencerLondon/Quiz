package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;

import java.util.List;

public class ChoiceTransformer {
    static List<Choice> toChoices(List<ChoiceDbo> choiceDbos) {
        return choiceDbos
                .stream()
                .map(ChoiceTransformer::toChoice).toList();
    }

    static List<ChoiceDbo> toChoiceDbos(List<Choice> choices) {
        return choices
                .stream()
                .map(ChoiceTransformer::toChoiceDbo)
                .toList();
    }

    static Choice toChoice(ChoiceDbo choiceDbo) {
        return new Choice(
                choiceDbo.getChoiceText(),
                choiceDbo.isCorrect()
        );
    }

    static ChoiceDbo toChoiceDbo(Choice choice) {
        ChoiceDbo choiceDbo = new ChoiceDbo();
        choiceDbo.setChoiceText(choice.text());
        choiceDbo.setCorrect(choice.isCorrect());
        return choiceDbo;
    }
}
