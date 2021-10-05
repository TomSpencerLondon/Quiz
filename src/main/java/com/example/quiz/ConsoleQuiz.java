package com.example.quiz;

public class ConsoleQuiz {
    public static void main(String[] args) {
        Question question1 = new Question("Question 1");
        Question question2 = new Question("Question 2");
        Question question3 = new Question("Question 3");
        Quiz quiz = new Quiz(question1, question2, question3);
        new Questioner(quiz).start();
    }

}
