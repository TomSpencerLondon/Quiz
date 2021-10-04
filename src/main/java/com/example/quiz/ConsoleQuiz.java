package com.example.quiz;

import java.util.Scanner;

public class ConsoleQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Question 1", "Question 2", "Question 3");
        final Scanner scanner = new Scanner(System.in);
        quiz.questions().forEach((q) -> {
            System.out.println(q);
            scanner.nextLine();
        });
    }
}
