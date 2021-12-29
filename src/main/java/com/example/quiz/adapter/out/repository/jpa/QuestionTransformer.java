package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Answer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionTransformer {

  @Autowired
  public QuestionTransformer() {}

  com.example.quiz.domain.Question toQuestion(Question questionDto) {
    final MultipleChoice multipleChoice = questionDto.getMultipleChoice();
    final List<Answer> answers = multipleChoice
        .getAnswers()
        .stream()
        .map(Answer::new)
        .collect(Collectors.toList());

    final com.example.quiz.domain.Question question = new com.example.quiz.domain.Question(
        questionDto.getText(),
        new com.example.quiz.domain.MultipleChoice(
            new Answer(multipleChoice.getCorrect()),
            answers
        ));

    question.setId(questionDto.getId());

    return question;
  }

  Question toQuestionDto(com.example.quiz.domain.Question question) {
    final List<String> answers = question.answers().stream().map(Answer::text).toList();
    final Answer correctAnswer = question.correctAnswer();
    final String questionText = question.text();

    final MultipleChoice multipleChoice = new MultipleChoice();
    final Question questionDto = new Question();
    multipleChoice.setCorrect(correctAnswer.text());
    multipleChoice.setAnswers(answers);
    questionDto.setText(questionText);
    questionDto.setMultipleChoice(multipleChoice);

    for (String answerValue : answers) {
      final com.example.quiz.adapter.out.repository.jpa.Answer answer = new com.example.quiz.adapter.out.repository.jpa.Answer();
      questionDto.addAnswer(answer);
      answer.setText(answerValue);
    }
    return questionDto;
  }
}
