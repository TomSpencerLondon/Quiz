package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import java.util.List;

public class TestQuizFactory {

  public static Quiz createQuizWithQuestions(int count) {
    final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    List<Choice> choices = List.of(
        new Choice("Answer 1")
    );
    MultipleChoice choice = new MultipleChoice(new Choice("Answer 1"), choices);

    for (int i = 1; i <= count; i++) {
      questionRepository.save(new Question("Question " + i, choice));
    }

    return new Quiz(questionRepository);
  }
}
