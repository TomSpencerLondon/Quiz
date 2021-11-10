package com.example.quiz.domain.port;

import com.example.quiz.domain.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

  Question save(Question question);

  List<Question> findAll();
}
