package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Answer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionTransformer {

  @Autowired
  public QuestionTransformer() {
  }

  com.example.quiz.domain.Question toQuestion(Question questionDto) {
    final List<Answer> answers = questionDto
        .getAnswers()
        .stream()
        .map((com.example.quiz.adapter.out.repository.jpa.Answer answer) -> new Answer(
            answer.getChoiceText()
        ))
        .collect(Collectors.toList());

    final Answer correctAnswer = questionDto
        .getAnswers()
        .stream()
        .filter((com.example.quiz.adapter.out.repository.jpa.Answer::isCorrect))
        .findFirst()
        .map((com.example.quiz.adapter.out.repository.jpa.Answer answer) -> new Answer(
            answer.getChoiceText()
        ))
        .get();

    final com.example.quiz.domain.Question question = new com.example.quiz.domain.Question(
        questionDto.getText(),
        new com.example.quiz.domain.MultipleChoice(
            correctAnswer,
            answers
        ));

    question.setId(questionDto.getId());

    return question;
  }

  Question toQuestionDto(com.example.quiz.domain.Question question) {
    final List<String> answers = question.answers().stream().map(Answer::text).toList();
    final Answer correctAnswer = question.correctAnswer();
    final String questionText = question.text();

    final Question questionDto = new Question();
    questionDto.setText(questionText);


    for (String answerValue : answers) {
      final com.example.quiz.adapter.out.repository.jpa.Answer answer = new com.example.quiz.adapter.out.repository.jpa.Answer();
      questionDto.addAnswer(answer);
      answer.setChoiceText(answerValue);
      answer.setCorrect(answerValue.equals(correctAnswer.text()));
    }
    return questionDto;
  }
}
