package com.example;

import com.example.quiz.adapter.in.web.AddQuestionForm;
import com.example.quiz.adapter.in.web.ChoiceForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class ChoiceValidator implements ConstraintValidator<CorrectAnswer, AddQuestionForm> {
    @Override
    public void initialize(CorrectAnswer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddQuestionForm addQuestionForm, ConstraintValidatorContext constraintValidatorContext) {
        long count = Stream.of(addQuestionForm.getChoice1(),
                                   addQuestionForm.getChoice2(),
                                   addQuestionForm.getChoice3(),
                                   addQuestionForm.getChoice4())
                           .filter(ChoiceForm::isCorrectAnswer)
                           .map(ChoiceForm::getChoice)
                           .count();
        if ((count < 1L || count > 1L) && addQuestionForm.getChoiceType().equals("single")) {
            return false;
        } else if ((count < 2L) && addQuestionForm.getChoiceType().equals("multiple")) {
            return false;
        }
        return true;
    }
}
