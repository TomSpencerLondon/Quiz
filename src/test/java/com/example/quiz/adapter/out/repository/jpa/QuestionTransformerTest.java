package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.junit.jupiter.api.Test;

class QuestionTransformerTest {
  final QuestionTransformer questionTransformer = new QuestionTransformer();

  @Test
  void questionDboToQuestion() {
    // Given
    final QuestionDbo questionDbo = new QuestionDbo();
    questionDbo.setId(1L);
    questionDbo.setText("Question 1");

    final AnswerDbo answerDbo1 = new AnswerDbo();
    answerDbo1.setQuestion(questionDbo);
    answerDbo1.setChoiceText("Answer 1");
    answerDbo1.setCorrect(true);

    final AnswerDbo answerDbo2 = new AnswerDbo();
    answerDbo2.setQuestion(questionDbo);
    answerDbo2.setChoiceText("Answer 2");
    answerDbo2.setCorrect(false);
    questionDbo.addAnswer(answerDbo1);
    questionDbo.addAnswer(answerDbo2);

    final Question expected = new Question(
        "Question 1",
        new MultipleChoice(
            new Choice("Answer 1"),
            List.of(new Choice("Answer 1"), new Choice("Answer 2"))
        )
    );
    expected.setId(1L);

    // When
    final Question question = questionTransformer.toQuestion(questionDbo);

    // Then
    assertThat(question)
        .isEqualTo(expected);
  }

  @Test
  void questionToQuestionDbo() {
    // Given
    final Question question = new Question("Question 1",
        new MultipleChoice(
            new Choice("Answer 1"),
            List.of(new Choice("Answer 1"), new Choice("Answer 2"))
        ));

    final QuestionDbo questionDbo = new QuestionDbo();
    questionDbo.setText("Question 1");

    final AnswerDbo answerDbo1 = new AnswerDbo();
    answerDbo1.setQuestion(questionDbo);
    answerDbo1.setChoiceText("Answer 1");
    answerDbo1.setCorrect(true);

    final AnswerDbo answerDbo2 = new AnswerDbo();
    answerDbo2.setQuestion(questionDbo);
    answerDbo2.setChoiceText("Answer 2");
    answerDbo2.setCorrect(false);
    questionDbo.addAnswer(answerDbo1);
    questionDbo.addAnswer(answerDbo2);

    // When
    final QuestionDbo result = questionTransformer.toQuestionDbo(question);

    assertThat(result)
        .isEqualTo(questionDbo);

  }
}