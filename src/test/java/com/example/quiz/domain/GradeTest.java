package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GradeTest {

  @Test
  void gradePercentCalculatedCorrectly() {
    final Grade grade = new Grade(3, 1, 2);
    assertThat(grade.percent())
        .isEqualTo(33);
  }

  @Test
  void returnsCorrectAndIncorrectValues() {
    final Grade grade = new Grade(3, 1, 2);

    assertThat(grade.correct())
        .isEqualTo(1);

    assertThat(grade.incorrect())
        .isEqualTo(2);
  }

}