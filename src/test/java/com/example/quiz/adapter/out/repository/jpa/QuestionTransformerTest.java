package com.example.quiz.adapter.out.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionFactory;
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
    expected.setId(1L);

    final QuestionDbo questionDbo = createQuestionDbo(expected);


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

    final QuestionDbo questionDbo = createQuestionDbo(question);

    // When
    final QuestionDbo result = questionTransformer.toQuestionDbo(question);

    assertThat(result)
        .isEqualTo(questionDbo);

  }

  private QuestionDbo createQuestionDbo(Question question) {
    final QuestionDbo questionDbo = new QuestionDbo();
    final Optional<Long> id = Optional.ofNullable(question.getId());
    id.ifPresent(questionDbo::setId);
    questionDbo.setText("Question 1");
    question.choices().forEach((a) -> {
      final ChoiceDbo answerDbo = new ChoiceDbo();
      answerDbo.setChoiceText(a.text());
      answerDbo.setCorrect(question.isCorrectAnswer(a));
      questionDbo.getChoices().add(answerDbo);
    });


    return questionDbo;
  }
}