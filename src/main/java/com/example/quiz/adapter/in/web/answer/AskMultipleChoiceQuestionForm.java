package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Question;

public class AskMultipleChoiceQuestionForm {
    public static AskMultipleChoiceQuestionForm from(Question question) {
        return new AskMultipleChoiceQuestionForm();
    }
}
