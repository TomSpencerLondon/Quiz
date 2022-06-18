package com.example.quiz.adapter.out.jpa;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.ChoiceId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceTransformer {
    List<Choice> toChoices(List<ChoiceDbo> choiceDbos) {
        return choiceDbos
                .stream()
                .map(this::toChoice).toList();
    }

    List<ChoiceDbo> toChoiceDbos(List<Choice> choices) {
        return choices
                .stream()
                .map(this::toChoiceDbo)
                .toList();
    }

    Choice toChoice(ChoiceDbo choiceDbo) {
        return new Choice(
                ChoiceId.of(choiceDbo.getId()),
                choiceDbo.getChoiceText(),
                choiceDbo.isCorrect()
        );
    }

    ChoiceDbo toChoiceDbo(Choice choice) {
        ChoiceDbo choiceDbo = new ChoiceDbo();
        choiceDbo.setChoiceText(choice.text());
        choiceDbo.setCorrect(choice.isCorrect());
        return choiceDbo;
    }
}
