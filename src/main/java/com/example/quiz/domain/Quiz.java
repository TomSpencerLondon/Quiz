package com.example.quiz.domain;

import java.util.List;

public class Quiz {

    @Deprecated // We want to use questionIds instead of questions
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

    // We want Quiz to have questionIds not Questions
    @Deprecated
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

    public List<QuestionId> questionIds() {
        return questionIds;
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

    public QuestionId firstQuestion() {
        return questionIds.get(0);
    }

    public int questionCount() {
        return questionIds.size();
    }
}