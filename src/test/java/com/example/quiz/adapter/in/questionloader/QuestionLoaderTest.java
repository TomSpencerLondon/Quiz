package com.example.quiz.adapter.in.questionloader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuestionLoaderTest {

  @Test
  void changes_question_text_to_a_question() {
    String inputText = """
        Using a Callable would be more appropriate than using a Runnable in which of the following situations?
                
        When you want to execute a task directly in a separate thread
        When your task might throw a checked exception and you want to execute it in a separate Thread
        When your task does not return any result but you want to execute the task asynchronously.
        When you want to use ExecutorService to submit multiple instances of a task.
                
        When your task might throw a checked exception and you want to execute it in a separate Thread.
        """;
    final QuestionLoader questionLoader = new QuestionLoader();

    List<Question> questions = questionLoader.parse(inputText);

    assertThat(questions.get(0).text())
        .isEqualTo(
            "Using a Callable would be more appropriate than using a Runnable in which of the following situations?");

    assertThat(questions.get(0).answers().containsAll(
        List.of(new Answer("When you want to execute a task directly in a separate thread"),
            new Answer(
                "When your task might throw a checked exception and you want to execute it in a separate Thread"),
            new Answer(
                "When your task does not return any result but you want to execute the task asynchronously."),
            new Answer(
                "When you want to use ExecutorService to submit multiple instances of a task."))
    ));

    assertThat(questions.get(0).correctAnswer())
        .isEqualTo(new Answer(
            "When your task might throw a checked exception and you want to execute it in a separate Thread."));
  }

  @Test
  void parsesTwoQuestions() {
    String inputText = """
        Using a Callable would be more appropriate than using a Runnable in which of the following situations?
                
        When you want to execute a task directly in a separate thread
        When your task might throw a checked exception and you want to execute it in a separate Thread
        When your task does not return any result but you want to execute the task asynchronously.
        When you want to use ExecutorService to submit multiple instances of a task.
                
        When your task might throw a checked exception and you want to execute it in a separate Thread.
        
        ---
        
        Given that a code fragment has just created a JDBC Connection and has executed an update statement, which of the following statements is correct?
        
        Changes to the database are pending a commit call on the connection.
        Changes to the database will be rolled back if another update is executed without commiting the previous update.
        Changes to the database will be committed right after the update statement has completed execution.
        A Connection is always in auto-commit mode when it is created.  As per the problem statement, an update was fired without explicitly disabling the auto-commit mode, the changes will be committed right after the update statement has finished execution.
        
        Changes to the database will be committed when another query (update or select) is fired using the connection.
        """;

    final QuestionLoader questionLoader = new QuestionLoader();

    List<Question> questions = questionLoader.parse(inputText);

    assertThat(questions.get(0).text())
        .isEqualTo(
            "Using a Callable would be more appropriate than using a Runnable in which of the following situations?");

    assertThat(questions.get(1).answers().containsAll(
    List.of(new Answer("Changes to the database are pending a commit call on the connection."),
            new Answer(
                "Changes to the database will be rolled back if another update is executed without commiting the previous update."),
            new Answer(
                "Changes to the database will be committed right after the update statement has completed execution."),
            new Answer(
                "A Connection is always in auto-commit mode when it is created.  As per the problem statement, an update was fired without explicitly disabling the auto-commit mode, the changes will be committed right after the update statement has finished execution.")
    )));

    assertThat(questions.get(0).correctAnswer())
        .isEqualTo(new Answer(
            "When your task might throw a checked exception and you want to execute it in a separate Thread."));


    assertThat(questions.get(1).text())
        .isEqualTo(
            "Given that a code fragment has just created a JDBC Connection and has executed an update statement, which of the following statements is correct?");

            assertThat(questions.get(1).answers().containsAll(
        List.of(new Answer("When you want to execute a task directly in a separate thread"),
            new Answer(
                "When your task might throw a checked exception and you want to execute it in a separate Thread"),
            new Answer(
                "When your task does not return any result but you want to execute the task asynchronously."),
            new Answer(
                "When you want to use ExecutorService to submit multiple instances of a task."))
    ));

    assertThat(questions.get(1).correctAnswer())
        .isEqualTo(new Answer(
            "Changes to the database will be committed when another query (update or select) is fired using the connection."));

  }
}
