package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Response;

public class ResponseView {

  private QuestionView questionView;
  private String chosenAnswer;
  private boolean correctlyAnswered;

  public static ResponseView from(Response response) {
    ResponseView responseView = new ResponseView();
    responseView.chosenAnswer = response.getChoice().text();
    responseView.questionView = QuestionView.of(response.getQuestion());
    responseView.correctlyAnswered = response.isCorrect();
    return responseView;
  }

  public QuestionView getQuestionView() {
    return questionView;
  }

  public String getChosenAnswer() {
    return chosenAnswer;
  }

  public boolean isCorrectlyAnswered() {
    return correctlyAnswered;
  }
}
