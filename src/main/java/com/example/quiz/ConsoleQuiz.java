package com.example.quiz;

public class ConsoleQuiz {
    public static void main(String[] args) {
        Question question1 = new Question(
                "Question 1",
                new MultipleChoice("Answer 2",
                                "Answer 1",
                                "Answer 2",
                                "Answer 3",
                                "Answer 4"
                        ));
        Question question2 = new Question(
                "Question 2",
                new MultipleChoice("Answer 3",
                        "Answer 1",
                        "Answer 2",
                        "Answer 3",
                        "Answer 4"
                )
        );
        Question question3 = new Question(
                "Question 3",
                new MultipleChoice("Answer 1",
                        "Answer 1",
                        "Answer 2",
                        "Answer 3",
                        "Answer 4"
                )
        );
        Quiz quiz = new Quiz(question1, question2, question3);
        new Questioner(quiz).start();
    }
}
