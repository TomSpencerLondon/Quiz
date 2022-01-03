package com.example.quiz.adapter.in.console;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.quiz.Quiz;
import com.example.quiz.domain.quiz.QuizSession;
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
      mark(session);
    }

    System.out.println(session.grade());
  }

  private void mark(QuizSession session) {
    session.respondWith(scanner.nextLine());
    System.out.println(session.lastResponseStatus());
  }

  private void print(Question q) {
    System.out.println(q);
  }
}
