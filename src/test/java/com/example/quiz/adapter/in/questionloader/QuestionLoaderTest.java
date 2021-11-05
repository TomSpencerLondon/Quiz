package com.example.quiz.adapter.in.questionloader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuestionLoaderTest {

  @Test
  void changes_question_text_to_a_question() {
    String questionText = """
        Using a Callable would be more appropriate than using a Runnable in which of the following situations?
                
        When you want to execute a task directly in a separate thread
        When your task might throw a checked exception and you want to execute it in a separate Thread
        When your task does not return any result but you want to execute the task asynchronously.
        When you want to use ExecutorService to submit multiple instances of a task.
                
        When your task might throw a checked exception and you want to execute it in a separate Thread.
        """;
    final QuestionLoader questionLoader = new QuestionLoader();

    Question question = questionLoader.parse(questionText);

    assertThat(question.text())
        .isEqualTo(
            "Using a Callable would be more appropriate than using a Runnable in which of the following situations?");

    assertThat(question.answers().containsAll(
        List.of(new Answer("When you want to execute a task directly in a separate thread"),
            new Answer(
                "When your task might throw a checked exception and you want to execute it in a separate Thread"),
            new Answer(
                "When your task does not return any result but you want to execute the task asynchronously."),
            new Answer(
                "When you want to use ExecutorService to submit multiple instances of a task."))
    ));

    assertThat(question.correctAnswer())
        .isEqualTo(new Answer(
            "When your task might throw a checked exception and you want to execute it in a separate Thread."));
  }
}
