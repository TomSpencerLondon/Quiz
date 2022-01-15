package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.SingleChoice;
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
    final List<Choice> choices = questionDbo
        .getChoices()
        .stream()
        .map((ChoiceDbo answerDbo) -> new Choice(
            answerDbo.getChoiceText()
        ))
        .collect(Collectors.toList());

    final Choice correctChoice = questionDbo
        .getChoices()
        .stream()
        .filter((ChoiceDbo::isCorrect))
        .findFirst()
        .map((ChoiceDbo answerDbo) -> new Choice(
            answerDbo.getChoiceText()
        ))
        .get();

    final com.example.quiz.domain.Question question = new com.example.quiz.domain.Question(
        questionDbo.getText(),
        new SingleChoice(
            correctChoice,
            choices
        ));

    question.setId(questionDbo.getId());

    return question;
  }

  QuestionDbo toQuestionDbo(com.example.quiz.domain.Question question) {
    final List<Choice> choices = question.choices();
    final String questionText = question.text();

    final QuestionDbo questionDbo = new QuestionDbo();
    questionDbo.setText(questionText);

    choices.forEach(choice -> {
      final ChoiceDbo answerDbo = new ChoiceDbo();
      questionDbo.getChoices().add(answerDbo);
      answerDbo.setChoiceText(choice.text());
      answerDbo.setCorrect(question.isCorrectAnswer(choice));
    });

    return questionDbo;
  }
}
