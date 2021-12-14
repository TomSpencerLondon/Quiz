package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDtoTransformer {

  @Autowired
  public QuestionDtoTransformer() {}

  com.example.quiz.domain.Question toQuestion(Question questionDto) {
    final MultipleChoiceDto multipleChoiceDto = questionDto.getMultipleChoiceDto();
    final List<Answer> answers = multipleChoiceDto
        .getAnswers()
        .stream()
        .map(Answer::new)
        .collect(Collectors.toList());

    final com.example.quiz.domain.Question question = new com.example.quiz.domain.Question(
        questionDto.getText(),
        new MultipleChoice(
            new Answer(multipleChoiceDto.getCorrect()),
            answers
        ));

    question.setId(questionDto.getId());

    return question;
  }

  Question toQuestionDto(com.example.quiz.domain.Question question) {
    final List<String> answers = question.answers().stream().map(Answer::text).toList();
    final Answer correctAnswer = question.correctAnswer();
    final String questionText = question.text();

    final MultipleChoiceDto multipleChoiceDto = new MultipleChoiceDto();
    final Question questionDto = new Question();
    multipleChoiceDto.setCorrect(correctAnswer.text());
    multipleChoiceDto.setAnswers(answers);
    questionDto.setText(questionText);
    questionDto.setMultipleChoiceDto(multipleChoiceDto);

    for (String answerValue : answers) {
      final com.example.quiz.adapter.out.repository.jpa.Answer answer = new com.example.quiz.adapter.out.repository.jpa.Answer();
      questionDto.addAnswer(answer);
      answer.setText(answerValue);
    }
    return questionDto;
  }
}
