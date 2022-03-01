package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.edit.QuestionView;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Response;

import java.util.List;

public class ResponseView {

    private QuestionView questionView;
    private List<String> chosenAnswers;
    private boolean correctlyAnswered;

    public static ResponseView from(Response response) {
        ResponseView responseView = new ResponseView();
        responseView.chosenAnswers = response.getChoices().stream().map(Choice::text).toList();
        responseView.questionView = QuestionView.of(response.getQuestion());
        responseView.correctlyAnswered = response.isCorrect();
        return responseView;
    }

    public QuestionView getQuestionView() {
        return questionView;
    }

    public List<String> getChosenAnswers() {
        return chosenAnswers;
    }

    public boolean isCorrectlyAnswered() {
        return correctlyAnswered;
    }
}
