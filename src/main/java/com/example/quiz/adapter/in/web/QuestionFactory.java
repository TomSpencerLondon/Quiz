package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;

import java.util.Arrays;
import java.util.List;

public class QuestionFactory {

    public static Question create(String questionText, ChoiceForm... choices) {
        checkForMoreThanOneCorrectChoice(choices);
        Choice correctChoice = extractCorrectChoiceFrom(choices);
        SingleChoice singleChoice = new SingleChoice(correctChoice, extractChoicesFrom(choices));
        return new Question(questionText, singleChoice);
    }

    private static List<Choice> extractChoicesFrom(ChoiceForm[] choices) {
        return Arrays.stream(choices)
                     .map(c -> new Choice(c.getChoice()))
                     .toList();
    }

    private static Choice extractCorrectChoiceFrom(ChoiceForm[] choices) {
        return Arrays.stream(choices)
                     .filter(ChoiceForm::isCorrectAnswer)
                     .map(c -> new Choice(c.getChoice()))
                     .findFirst()
                     .orElseThrow(() -> new NoCorrectChoiceSelected(choices));
    }

    private static void checkForMoreThanOneCorrectChoice(ChoiceForm[] choices) {
        long count = Arrays.stream(choices)
                           .filter(ChoiceForm::isCorrectAnswer)
                           .count();
        if (count > 1) {
            throw new TooManyCorrectChoicesSelected(choices);
        }
    }

}
