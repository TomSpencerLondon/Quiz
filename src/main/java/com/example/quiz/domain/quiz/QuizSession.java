package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;

public interface QuizSession {
  Question question();
  void respondWith(String text, Question question);
  ResponseStatus lastResponseStatus();
}
