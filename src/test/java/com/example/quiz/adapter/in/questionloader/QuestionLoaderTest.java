package com.example.quiz.adapter.in.questionloader;

import static org.assertj.core.api.Assertions.*;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuestionLoaderTest {

  @Test
  void changes_question_text_to_a_question() {
    String inputText = """
        Q1
                
        Q1A1
        Q1A2
        Q1A3
        Q1A4
                
        Q1A2
        """;
    final QuestionLoader questionLoader = new QuestionLoader();

    List<Question> questions = questionLoader.parse(inputText);

    final Question question1 = createQuestion("Q1", "Q1A2", "Q1A1", "Q1A2", "Q1A3", "Q1A4");

    assertThat(questions)
        .containsExactly(question1);
  }

  @Test
  void parsesTwoQuestions() {
    String inputText = """
        Q1
                
        Q1A1
        Q1A2
        Q1A3
        Q1A4
                
        Q1A2
                
        ---
                
        Q2
                
        Q2A1
        Q2A2
        Q2A3
        Q2A4
                
        Q2A3
        """;

    final QuestionLoader questionLoader = new QuestionLoader();

    List<Question> questions = questionLoader.parse(inputText);

    final Question question1 = createQuestion("Q1", "Q1A2", "Q1A1", "Q1A2", "Q1A3", "Q1A4");
    final Question question2 = createQuestion("Q2", "Q2A3", "Q2A1", "Q2A2", "Q2A3", "Q2A4");

    assertThat(questions)
        .containsExactly(question1, question2);
  }

  private Question createQuestion(String questionText, String correctAnswer, String answer1,
      String answer2, String answer3, String answer4) {
    return new Question(questionText,
        new MultipleChoice(
            new Answer(correctAnswer),
            List.of(
                new Answer(answer1),
                new Answer(answer2), new Answer(answer3),
                new Answer(answer4))
        ));
  }
}
