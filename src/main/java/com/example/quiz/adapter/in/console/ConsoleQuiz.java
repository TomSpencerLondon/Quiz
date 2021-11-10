package com.example.quiz.adapter.in.console;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ConsoleQuiz implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
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
