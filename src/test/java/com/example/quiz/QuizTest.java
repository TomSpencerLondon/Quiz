package com.example.quiz;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {
    @Test
    void new_quiz_hasNoQuestions() {
        // Given / when
        Quiz quiz = new Quiz();

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        Quiz quiz = new Quiz(new Question(
                "Question 1",
                new MultipleChoice(
                "Answer 2",
                "Answer 1",
                "Answer 2")
        ));

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(new Question(
                "Question 1",
                new MultipleChoice("Answer 2")
        ));
    }

    @Test
    void new_quiz_hasManyQuestions() {
        // Given / when
        Quiz quiz = new Quiz(new Question("Question 1",
                new MultipleChoice("Question 1")
        ),
                new Question("Question 2",
                new MultipleChoice("Answer 2")
                ));

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(
                new Question("Question 1",
                        new MultipleChoice("Answer 1")
                ),
                new Question("Question 2",
                        new MultipleChoice("Answer 2")
                )
        );
    }
}
