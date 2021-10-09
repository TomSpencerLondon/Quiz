package com.example.quiz;

import java.util.Scanner;

class Questioner {
    private Quiz quiz;
    private Scanner scanner = new Scanner(System.in);

    public Questioner(Quiz quiz) {
        this.quiz = quiz;
    }

    public void start() {
        quiz.questions().forEach((q) -> {
            print(q);
            check(q, new Answer(scanner.nextLine()));
        });

        grade();
    }

    private void grade() {
        System.out.println(quiz.grade());
    }

    private void check(Question question, Answer answer) {
        Question markedQuestion = quiz.mark(question, answer);
        System.out.println(markedQuestion.status());
    }

    private void print(Question q) {
        System.out.println(q);
    }
}
