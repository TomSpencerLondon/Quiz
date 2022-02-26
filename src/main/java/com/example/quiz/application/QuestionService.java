package com.example.quiz.application;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;
import com.example.quiz.adapter.in.web.edit.NoCorrectChoiceSelected;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Component
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question add(AddQuestionForm addQuestionForm) {
        List<ChoiceForm> choiceForms = Stream.of(addQuestionForm.getChoice1(), addQuestionForm.getChoice2(), addQuestionForm.getChoice3(), addQuestionForm.getChoice4())
                                             .toList();
        if (addQuestionForm.getChoiceType().equals("single")) {
            Choice correctChoice = extractCorrectChoiceFrom(choiceForms);
            SingleChoice singleChoice = new SingleChoice(correctChoice, extractChoicesFrom(choiceForms));
            Question question = new Question(addQuestionForm.getText(), singleChoice);
            return questionRepository.save(question);
        } else {
            List<Choice> correctChoices = extractCorrectChoicesFrom(choiceForms);
            MultipleChoice multipleChoice = new MultipleChoice(correctChoices, extractChoicesFrom(choiceForms));
            Question question = new Question(addQuestionForm.getText(), multipleChoice);
            return questionRepository.save(question);
        }
    }


    private static List<Choice> extractChoicesFrom(List<ChoiceForm> choices) {
        return choices.stream().map(ChoiceForm::getChoice).map(Choice::new).toList();
    }

    private static Choice extractCorrectChoiceFrom(List<ChoiceForm> choices) {
        return choices.stream()
                      .filter(ChoiceForm::isCorrectAnswer)
                      .map(c -> new Choice(c.getChoice()))
                      .findFirst()
                      .orElseThrow(() -> new NoCorrectChoiceSelected(choices.toArray(new ChoiceForm[0])));
    }

    private static List<Choice> extractCorrectChoicesFrom(List<ChoiceForm> choices) {
        return choices.stream()
                      .filter(ChoiceForm::isCorrectAnswer)
                      .map(c -> new Choice(c.getChoice()))
                      .toList();
    }
}
