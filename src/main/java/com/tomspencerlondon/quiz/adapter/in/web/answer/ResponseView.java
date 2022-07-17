package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.adapter.in.web.edit.QuestionView;
import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.Response;

import java.util.List;

public class ResponseView {

    private QuestionView questionView;
    private List<String> chosenAnswers;
    private boolean correctlyAnswered;

    public static ResponseView from(Response response, QuestionRepository questionRepository) {
        ResponseView responseView = new ResponseView();
        Question question = questionRepository
                .findById(response.questionId())
                .orElseThrow();

        responseView.chosenAnswers = question.choices()
                                             .stream()
                                             .filter(choice -> response.choiceIds().contains(choice.getId()))
                                             .map(Choice::text)
                                             .toList();


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
