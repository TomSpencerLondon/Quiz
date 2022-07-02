package com.tomspencerlondon.quiz.hexagon.domain;

import java.util.List;

public interface ChoiceType {
    List<Choice> choices();

    boolean isCorrect(Choice... choices);

    boolean isSingleChoice();
}
