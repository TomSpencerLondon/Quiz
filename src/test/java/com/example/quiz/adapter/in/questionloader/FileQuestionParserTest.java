package com.example.quiz.adapter.in.questionloader;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Question;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileQuestionParserTest {
  private QuestionFactory questionFactory;
  private QuestionParser questionLoader;
  private FileQuestionParser fileQuestionParser;

  @BeforeEach
  void setUp() {
    questionLoader = new QuestionParser();
    fileQuestionParser = new FileQuestionParser(questionLoader);
    questionFactory = new QuestionFactory();
  }

  @Test
  void retrieves_text_from_a_file() throws URISyntaxException, IOException {
    final Question question1 = questionFactory.createQuestion(
        List.of("Q1", "Q1A1", "Q1A2", "Q1A3",
            "Q1A4", "Q1A2"));
    final Question question2 = questionFactory.createQuestion(List.of("Q2", "Q2A1", "Q2A2", "Q2A3",
        "Q2A4", "Q2A3"));
    final Question question3 = questionFactory.createQuestion(List.of("Q3", "Q3A1", "Q3A2", "Q3A3",
        "Q3A4", "Q3A4"));

    final List<Question> output = fileQuestionParser.read("questions.txt");

    assertThat(output)
        .containsExactly(question1, question2, question3);
  }
}
