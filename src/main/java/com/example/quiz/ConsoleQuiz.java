package com.example.quiz;
import java.util.List;

public class ConsoleQuiz {
    public static void main(String[] args) {
        QuestionStatus status = QuestionStatus.PENDING;
        List<Answer> answers =
                List.of(new Answer("Answer 1"),
                        new Answer("Answer 2"),
                        new Answer("Answer 3"),
                        new Answer("Answer 4"));
        Question question1 = new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 2"),
                        answers), status);
        Question question2 = new Question(
                "Question 2",
                new MultipleChoice(new Answer("Answer 3"),
                        answers),
                status
        );
        Question question3 = new Question(
                "Question 3",
                new MultipleChoice(new Answer("Answer 1"),
                        answers
                ),
                status
        );
        Quiz quiz = new Quiz(question1, question2, question3);
        new Questioner(quiz).start();
    }
}
