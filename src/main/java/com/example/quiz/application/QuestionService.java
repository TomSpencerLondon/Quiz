package com.example.quiz.application;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import com.example.quiz.adapter.in.web.edit.NoCorrectChoiceSelected;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question add(AddQuestionForm addQuestionForm) {
        List<ChoiceForm> choiceForms = Arrays.stream(addQuestionForm.getChoices())
                                             .toList();
        if (addQuestionForm.getChoiceType().equals("single")) {
            List<Choice> choices = extractChoicesFrom(choiceForms);
            ChoiceType singleChoice = new SingleChoice(choices);
            Question question = new Question(addQuestionForm.getText(), singleChoice);
            return questionRepository.save(question);
        } else {
            List<Choice> choices = extractChoicesFrom(choiceForms);
            MultipleChoice multipleChoice = new MultipleChoice(choices);
            Question question = new Question(addQuestionForm.getText(), multipleChoice);
            return questionRepository.save(question);
        }
    }


    private static List<Choice> extractChoicesFrom(List<ChoiceForm> choices) {
        return choices.stream().map(c -> new Choice(c.getChoice(), c.isCorrectAnswer())).toList();
    }

    private static Choice extractCorrectChoiceFrom(List<ChoiceForm> choices) {
        return choices.stream()
                      .filter(ChoiceForm::isCorrectAnswer)
                      .map(c -> new Choice(c.getChoice(), true))
                      .findFirst()
                      .orElseThrow(() -> new NoCorrectChoiceSelected(choices.toArray(new ChoiceForm[0])));
    }

}
