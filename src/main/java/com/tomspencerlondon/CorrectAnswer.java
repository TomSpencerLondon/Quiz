package com.tomspencerlondon;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChoiceValidator.class)
@Documented
public @interface CorrectAnswer {
    String message() default "missing correct number of answers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
