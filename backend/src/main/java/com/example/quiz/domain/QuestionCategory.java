package com.example.quiz.domain;

import java.util.List;

public interface QuestionCategory {
  List<Choice> choices();
  boolean isCorrect(Choice... choices);
}
