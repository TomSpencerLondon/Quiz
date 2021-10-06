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
    }

    private void check(Question question, Answer answer) {
        System.out.println(question.check(answer));
    }

    private void print(Question q) {
        System.out.println(q);
    }
}
