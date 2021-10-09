package com.example.quiz;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;

public class Quiz {

    private List<Question> questions = new ArrayList<>();

    public Quiz(Question... questions) {
        this.questions.addAll(asList(questions));
    }

    public List<Question> questions() {
        return questions;
    }

    public Question mark(Question question, Answer answer) {
        int index = questions.indexOf(question);
        final Question markedQuestion = question.mark(answer);
        questions.set(index, markedQuestion);
        return markedQuestion;
    }

    public Grade grade() {
        AtomicInteger correct = new AtomicInteger();
        AtomicInteger incorrect = new AtomicInteger();
        AtomicInteger pending = new AtomicInteger();
        this.questions.forEach((q) -> {
            switch (q.status()) {
                case CORRECT:
                    correct.getAndIncrement();
                    break;
                case INCORRECT:
                    incorrect.getAndIncrement();
                    break;
                default:
                    pending.getAndIncrement();
                    break;
            }
        });

        FinalMark finalMark = new FinalMark(pending.get(), correct.get(), incorrect.get());

        return new Grade(this.questions.size(), finalMark);
    }
}
