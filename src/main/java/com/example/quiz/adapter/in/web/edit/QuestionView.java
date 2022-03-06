package com.example.quiz.adapter.in.web.edit;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;

import java.util.List;

public class QuestionView {

    private final String text;
    private final String answer;
    private final String choice1;
    private final String choice2;
    private final String choice3;
    private final String choice4;

    public QuestionView(String text, String answer, String choice1, String choice2,
                        String choice3, String choice4) {
        this.text = text;
        this.answer = answer;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    public static QuestionView of(Question question) {
        if (question.isSingleChoice()) {
            final Choice correct = question.choices().stream()
                                           .filter(question::isCorrectAnswer).findFirst().orElseThrow();
            return new QuestionView(
                    question.text(),
                    correct.text(),
                    question.choices().get(0).text(),
                    question.choices().get(1).text(),
                    question.choices().get(2).text(),
                    question.choices().get(3).text()
            );
        } else {
            List<Choice> correct = question.choices().stream()
                                           .filter(Choice::isCorrect).toList();
            return new QuestionView(
                    question.text(),
                    correct.toString(),
                    question.choices().get(0).text(),
                    question.choices().get(1).text(),
                    question.choices().get(2).text(),
                    question.choices().get(3).text()
            );
        }
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }

}
