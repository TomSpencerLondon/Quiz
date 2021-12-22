package com.example.quiz.adapter.in.console;
import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import com.example.quiz.domain.quiz.Quiz;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

@Profile("!test")
//@Component - commented out (otherwise it will halt the application)
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
        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
        final Quiz quiz = new Quiz(questionRepository);
        new Questioner(quiz).start();
    }
}
