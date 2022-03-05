package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionTransformer.class);

    @Autowired
    public QuestionTransformer() {
    }

    Question toQuestion(QuestionDbo questionDbo) {
        final List<Choice> choices = questionDbo
                .getChoices()
                .stream()
                .map((ChoiceDbo answerDbo) -> new Choice(
                        answerDbo.getChoiceText(),
                        answerDbo.isCorrect()
                ))
                .collect(Collectors.toList());

        final Choice correctChoice = questionDbo
                .getChoices()
                .stream()
                .filter((ChoiceDbo::isCorrect))
                .findFirst()
                .map((ChoiceDbo answerDbo) -> new Choice(
                        answerDbo.getChoiceText(),
                        answerDbo.isCorrect()
                )).orElseGet(() -> {
                    LOGGER.error("No correct answer for QuestionDbo with id: {}", questionDbo.getId());
                    return choices.get(0);
                });

        final Question question = new com.example.quiz.domain.Question(
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
            final ChoiceDbo choiceDbo = new ChoiceDbo();
            questionDbo.getChoices().add(choiceDbo);
            choiceDbo.setChoiceText(choice.text());
            if (question.isSingleChoice()) {
                choiceDbo.setCorrect(question.isCorrectAnswer(choice));
            }else {
                choiceDbo.setCorrect(choice.isCorrect());
            }
        });

        return questionDbo;
    }
}
