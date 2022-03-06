package com.example.quiz.adapter.out.repository.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                )).toList();

        final Question question = new com.example.quiz.domain.Question(
                questionDbo.getText(),
                new SingleChoice(choices));

        question.setId(questionDbo.getId());

        return question;
    }

    QuestionDbo toQuestionDbo(com.example.quiz.domain.Question question) {
        List<Choice> choices = question.choices();
        String questionText = question.text();

        QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText(questionText);

        List<ChoiceDbo> choiceDbos = new ArrayList<>();
        for (Choice choice : choices) {
            ChoiceDbo choiceDbo = new ChoiceDbo();
            questionDbo.getChoices().add(choiceDbo);
            choiceDbo.setChoiceText(choice.text());
            choiceDbo.setCorrect(choice.isCorrect());
            choiceDbos.add(choiceDbo);
        }

        questionDbo.setChoices(choiceDbos);

        return questionDbo;
    }
}
