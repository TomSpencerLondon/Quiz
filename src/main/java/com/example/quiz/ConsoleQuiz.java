package com.example.quiz;
import java.util.List;

public class ConsoleQuiz {
    public static void main(String[] args) {
        List<Answer> answers =
                List.of(new Answer("Answer 1"),
                        new Answer("Answer 2"),
                        new Answer("Answer 3"),
                        new Answer("Answer 4"));
        Question question1 = new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 2"),
                        answers));
        Question question2 = new Question(
                "Question 2",
                new MultipleChoice(new Answer("Answer 3"),
                        answers)
        );
        Question question3 = new Question(
                "Question 3",
                new MultipleChoice(new Answer("Answer 1"),
                        answers
                )
        );
        Quiz quiz = new Quiz(question1, question2, question3);
        new Questioner(quiz).start();
    }
}
