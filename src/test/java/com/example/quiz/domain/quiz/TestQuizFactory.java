package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import java.util.List;

public class TestQuizFactory {

  public static Quiz createQuizWithQuestions(int count) {
    final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    List<Answer> answers = List.of(
        new Answer("Answer 1")
    );
    MultipleChoice choice = new MultipleChoice(new Answer("Answer 1"), answers);

    for (int i = 1; i <= count; i++) {
      questionRepository.save(new Question("Question " + i, choice));
    }

    return new Quiz(questionRepository);
  }
}
