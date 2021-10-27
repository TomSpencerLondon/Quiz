package com.example.quiz.adapter.port.repository.jpa;

import org.springframework.data.annotation.Id;

public class Question {

  private String id;

  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }
}
