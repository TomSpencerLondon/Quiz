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

  com.example.quiz.domain.Question toQuestion(QuestionDbo questionDbo) {
    final List<Answer> answers = questionDbo
        .getAnswers()
        .stream()
        .map((AnswerDbo answerDbo) -> new Answer(
            answerDbo.getChoiceText()
        ))
        .collect(Collectors.toList());

    final Answer correctAnswer = questionDbo
        .getAnswers()
        .stream()
        .filter((AnswerDbo::isCorrect))
        .findFirst()
        .map((AnswerDbo answerDbo) -> new Answer(
            answerDbo.getChoiceText()
        ))
        .get();

    final com.example.quiz.domain.Question question = new com.example.quiz.domain.Question(
        questionDbo.getText(),
        new com.example.quiz.domain.MultipleChoice(
            correctAnswer,
            answers
        ));

    question.setId(questionDbo.getId());

    return question;
  }

  QuestionDbo toQuestionDbo(com.example.quiz.domain.Question question) {
    final List<String> answers = question.answers().stream().map(Answer::text).toList();
    final Answer correctAnswer = question.correctAnswer();
    final String questionText = question.text();

    final QuestionDbo questionDbo = new QuestionDbo();
    questionDbo.setText(questionText);


    for (String answerValue : answers) {
      final AnswerDbo answerDbo = new AnswerDbo();
      questionDbo.addAnswer(answerDbo);
      answerDbo.setChoiceText(answerValue);
      answerDbo.setCorrect(answerValue.equals(correctAnswer.text()));
    }
    return questionDbo;
  }
}
