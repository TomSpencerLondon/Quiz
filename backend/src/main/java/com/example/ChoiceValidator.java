package com.example;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ChoiceValidator implements ConstraintValidator<CorrectAnswer, AddQuestionForm> {
    @Override
    public void initialize(CorrectAnswer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AddQuestionForm addQuestionForm, ConstraintValidatorContext constraintValidatorContext) {
        long count = Arrays.stream(addQuestionForm.getChoices())
                           .filter(ChoiceForm::isCorrectAnswer)
                           .map(ChoiceForm::getChoice)
                           .count();
        if (tooManyForSingleChoice(addQuestionForm, count) || tooFewForMultipleChoice(addQuestionForm, count)) {
            return false;
        }
        return true;
    }

    private boolean tooFewForMultipleChoice(AddQuestionForm addQuestionForm, long count) {
        return (count < 2L) && addQuestionForm.getChoiceType().equals("multiple");
    }

    private boolean tooManyForSingleChoice(AddQuestionForm addQuestionForm, long count) {
        return (count < 1L || count > 1L) && addQuestionForm.getChoiceType().equals("single");
    }
}
