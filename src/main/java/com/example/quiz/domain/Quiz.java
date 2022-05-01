package com.example.quiz.domain;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private String quizName;
    private QuizId quizId;

    private List<QuestionId> questionIds;

    public void setId(QuizId id) {
        this.quizId = id;
    }

    public QuizId getId() {
        return quizId;
    }

    public Quiz(List<Question> questions) {
        this.questions = questions;
        questionIds = questions.stream()
                               .map(Question::getId).toList();
    }

    public Quiz(String quizName, List<QuestionId> questionIds) {
        this.quizName = quizName;
        this.questionIds = questionIds;
    }

    @Deprecated
    public List<Question> questions() {
        return questions;
    }

    public QuizSession start() {
        return new QuizSession(this);
    }

    public QuestionId nextQuestionAfter(QuestionId questionId) {
        int index = questionIds.indexOf(questionId);
        // if index < 0 throw error
        int nextIndex = index + 1;
        return nextIndex < questionIds.size()
                ? questionIds.get(nextIndex)
                : questionId;
    }

    public boolean isLastQuestion(QuestionId questionId) {
        int index = questionIds.indexOf(questionId);
        return index == questionIds.size() - 1;
    }
}