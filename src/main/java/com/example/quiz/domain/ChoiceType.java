package com.example.quiz.domain;

import java.util.List;

public interface ChoiceType {
    List<Choice> choices();

    boolean isCorrect(Choice... choices);

    boolean isSingleChoice();
}
