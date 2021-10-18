package com.example.quiz;

import java.util.Scanner;

class Questioner {
  private Quiz quiz;
  private Scanner scanner = new Scanner(System.in);

  public Questioner(Quiz quiz) {
    this.quiz = quiz;
  }

  public void start() {
    QuizSession session = quiz.start();
    while (!session.isFinished()) {
      Question question = session.question();
      print(question);
      mark(session, question);
    }
  }

  private void mark(QuizSession session, Question question) {
    session.respondWith(scanner.nextLine(), question);
    System.out.println(session.lastResponseStatus());
  }

  private void print(Question q) {
    System.out.println(q);
  }
}
