package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Grade;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GradeTest {

    @Test
    void gradePercentCalculatedCorrectly() {
        final Grade grade = new GradeBuilder()
                .withCorrectSingleChoice()
                .withIncorrectSingleChoice()
                .withIncorrectSingleChoice()
                .build();

        assertThat(grade.percent())
                .isEqualTo(33);
    }

    @Test
    void returnsCorrectAndIncorrectValues() {
        final Grade grade = new GradeBuilder()
                .withCorrectSingleChoice()
                .withIncorrectSingleChoice()
                .withIncorrectSingleChoice()
                .build();

        assertThat(grade.correct())
                .isEqualTo(1);

        assertThat(grade.incorrect())
                .isEqualTo(2);
    }
}