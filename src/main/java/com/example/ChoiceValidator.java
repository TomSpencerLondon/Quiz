package com.example;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.ChoiceForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ChoiceValidator implements ConstraintValidator<CorrectAnswer, AddQuestionForm> {
    @Override
    public boolean isValid(AddQuestionForm addQuestionForm, ConstraintValidatorContext constraintValidatorContext) {
        long count = Arrays.stream(addQuestionForm.getChoices())
                           .filter(ChoiceForm::isCorrectAnswer)
                           .count();
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (tooManyForSingleChoice(addQuestionForm, count)){
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    String.format("%d for single choice", count)).addConstraintViolation();
            return false;
        } else if (tooFewForMultipleChoice(addQuestionForm, count)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    String.format("%d for multiple choice", count)).addConstraintViolation();
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
