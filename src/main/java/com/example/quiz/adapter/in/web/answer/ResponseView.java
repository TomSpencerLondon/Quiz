package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.adapter.in.web.edit.QuestionView;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Response;

import java.util.List;

public class ResponseView {

    private QuestionView questionView;
    private List<String> chosenAnswers;
    private boolean correctlyAnswered;

    public static ResponseView from(Response response, QuestionRepository questionRepository) {
        ResponseView responseView = new ResponseView();
        responseView.chosenAnswers = response.choices().stream().map(Choice::text).toList();
        Question question = questionRepository
                .findById(response.questionId())
                .orElseThrow();

        responseView.questionView = QuestionView.of(question);
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
