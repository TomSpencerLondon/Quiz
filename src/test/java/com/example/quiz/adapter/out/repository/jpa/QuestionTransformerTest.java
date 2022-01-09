package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.quiz.QuestionFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class QuestionTransformerTest {
  final QuestionTransformer questionTransformer = new QuestionTransformer();

  @Test
  void questionDboToQuestion() {
    // Given
    final Question expected = QuestionFactory.create(
        "Question 1",
        "Answer 1",
        "Answer 2",
        "Answer 3",
        "Answer 4",
        "Answer 1"
    );
    final long id = 1L;
    expected.setId(id);
    final QuestionDbo questionDbo = createQuestionDbo(Optional.of(id), expected);


    // When
    final Question question = questionTransformer.toQuestion(questionDbo);

    // Then
    assertThat(question)
        .isEqualTo(expected);
  }

  @Test
  void questionToQuestionDbo() {
    // Given
    final Question question = QuestionFactory.create(
        "Question 1",
        "Answer 1",
        "Answer 2",
        "Answer 3",
        "Answer 4",
        "Answer 1"
        );

    final QuestionDbo questionDbo = createQuestionDbo(Optional.empty(), question);

    // When
    final QuestionDbo result = questionTransformer.toQuestionDbo(question);

    assertThat(result)
        .isEqualTo(questionDbo);

  }

  private QuestionDbo createQuestionDbo(Optional<Long> id, Question question) {
    final QuestionDbo questionDbo = new QuestionDbo();
    id.ifPresent(questionDbo::setId);
    questionDbo.setText("Question 1");
    question.answers().forEach((a) -> {
      final AnswerDbo answerDbo = new AnswerDbo();
      answerDbo.setQuestion(questionDbo);
      answerDbo.setChoiceText(a.text());
      answerDbo.setCorrect(question.isCorrectAnswer(a));
      questionDbo.addAnswer(answerDbo);
    });


    return questionDbo;
  }
}