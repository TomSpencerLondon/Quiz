package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;

import java.util.Arrays;
import java.util.List;

public class QuestionView {
    private final String text;
    private final String answer;
    private final List<String> choices;

    public QuestionView(String text, String answer, String... choices) {
        this.text = text;
        this.answer = answer;
        this.choices = Arrays.stream(choices).toList();
    }

    public static QuestionView of(Question question) {
        if (question.isSingleChoice()) {
            final Choice correct = question.choices().stream()
                                           .filter(question::isCorrectAnswer).findFirst().orElseThrow();
            return new QuestionView(
                    question.text(),
                    correct.text(),
                    question.choices().stream().map(Choice::text).toArray(String[]::new)
            );
        } else {
            List<Choice> correct = question.choices().stream()
                                           .filter(Choice::isCorrect).toList();
            return new QuestionView(
                    question.text(),
                    correct.toString(),
                    question.choices().stream().map(Choice::text).toArray(String[]::new)
            );
        }
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getChoices() {
        return choices;
    }
}
