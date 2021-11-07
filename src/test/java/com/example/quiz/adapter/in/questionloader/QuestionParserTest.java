package com.example.quiz.adapter.in.questionloader;

import static org.assertj.core.api.Assertions.*;

import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

public class QuestionParserTest {

  private final QuestionFactory questionFactory = new QuestionFactory();

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
    final QuestionParser questionLoader = new QuestionParser();

    List<Question> questions = questionLoader.parse(inputText);

    final Question question1 = questionFactory.createQuestion(List.of("Q1", "Q1A2", "Q1A1", "Q1A2", "Q1A3",
        "Q1A4"));

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

    final QuestionParser questionLoader = new QuestionParser();

    List<Question> questions = questionLoader.parse(inputText);

    final Question question1 = questionFactory.createQuestion(List.of("Q1", "Q1A2", "Q1A1", "Q1A2", "Q1A3",
        "Q1A4"));
    final Question question2 = questionFactory.createQuestion(List.of("Q2", "Q2A3", "Q2A1", "Q2A2", "Q2A3",
        "Q2A4"));

    assertThat(questions)
        .containsExactly(question1, question2);
  }

  @Test
  void parsesThreeQuestions() {
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
        
        ---
                
        Q3
                
        Q3A1
        Q3A2
        Q3A3
        Q3A4
                
        Q3A4

        """;

    final QuestionParser questionLoader = new QuestionParser();

    List<Question> questions = questionLoader.parse(inputText);

    final Question question1 = questionFactory.createQuestion(List.of("Q1", "Q1A1", "Q1A2", "Q1A3",
        "Q1A4", "Q1A2"));
    final Question question2 = questionFactory.createQuestion(List.of("Q2", "Q2A1", "Q2A2", "Q2A3",
        "Q2A4", "Q2A3"));
    final Question question3 = questionFactory.createQuestion(List.of("Q3", "Q3A1", "Q3A2", "Q3A3",
        "Q3A4", "Q3A4"));

    assertThat(questions)
        .containsExactly(question1, question2, question3);
  }
}
