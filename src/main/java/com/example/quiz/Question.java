package com.example.quiz;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Question {
    private final MultipleChoice choice;
    private String text;

    public Question(
            String text,
            MultipleChoice choice
    ) {
        this.text = text;
        this.choice = choice;
    }

    public Question(String text) {
        this.text = text;
        this.choice = new MultipleChoice();
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.text);
        if (this.choice.answers().isEmpty()){
            return sb.toString();
        } else {
            this.choice.answers().forEach((a) -> {
                sb.append("\n");
                sb.append(a);
            });
        }

        return sb.toString();
    }

    public List<Answer> answers() {
        return this.choice.answers();
    }

    public Optional<Answer> correctAnswer() {
        if (choice.correctAnswer().isPresent()) {
            return choice.correctAnswer();
        } else {
            return Optional.empty();
        }
    }

    public String check(Answer answer) {
        if(answer.equals(this.correctAnswer().get())){
            return "Correct!";
        } else {
            return "Incorrect.";
        }
    }
}
