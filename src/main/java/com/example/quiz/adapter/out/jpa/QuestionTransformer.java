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

        Question question = new Question(
                questionDbo.getText(),
                choiceType(questionDbo, choices));

        question.setId(questionDbo.getId());

        return question;
    }

    QuestionDbo toQuestionDbo(Question question) {
        QuestionDbo questionDbo = new QuestionDbo();
        questionDbo.setText(question.text());
        ChoiceType choiceType = question.isSingleChoice() ? ChoiceType.SINGLE : ChoiceType.MULTIPLE;
        questionDbo.setChoiceType(choiceType);
        List<ChoiceDbo> choiceDbos = ChoiceTransformer.toChoiceDbos(question.choices());
        questionDbo.setChoices(choiceDbos);
        return questionDbo;
    }

    private com.example.quiz.domain.ChoiceType choiceType(QuestionDbo questionDbo, List<Choice> choices) {
        return questionDbo.getChoiceType().equals(ChoiceType.SINGLE) ?
                new SingleChoice(choices) : new MultipleChoice(choices);
    }

}
