package com.example.quiz;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Question {
    private final MultipleChoice choice;
    private final QuestionStatus status;
    private String text;

    public Question(
            String text,
            MultipleChoice choice,
            QuestionStatus status
    ) {
        this.text = text;
        this.choice = choice;
        this.status = status;
    }

    public Question(String text, QuestionStatus status) {
        this.text = text;
        this.choice = new MultipleChoice();
        this.status = status;
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

    public String mark(Answer answer) {
        if(answer.equals(this.correctAnswer().get())){
            return "Correct!";
        } else {
            return "Incorrect.";
        }
    }

    public QuestionStatus status() {
        return this.status;
    }
}
