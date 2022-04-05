package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTransformer {
    Question toQuestion(QuestionDbo questionDbo) {
        List<Choice> choices = ChoiceTransformer.toChoices(questionDbo
                .getChoices());

        com.example.quiz.domain.ChoiceType choiceType = choices.stream().filter(Choice::isCorrect).count() < 2 ?
                new SingleChoice(choices) : new MultipleChoice(choices);

        Question question = new Question(
                questionDbo.getText(),
                choiceType);

        question.setId(questionDbo.getId());

        return question;
    }

    QuestionDbo toQuestionDbo(Question question) {
        QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText(question.text());
        List<ChoiceDbo> choiceDbos = ChoiceTransformer.toChoiceDbos(question.choices());
        questionDbo.setChoices(choiceDbos);
        return questionDbo;
    }

}
