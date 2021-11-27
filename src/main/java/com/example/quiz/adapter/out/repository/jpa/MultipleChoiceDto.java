package com.example.quiz.adapter.out.repository.jpa;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MultipleChoiceDto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String correct;

  @ElementCollection
  private List<String> answers;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCorrect() {
    return correct;
  }

  public void setCorrect(String correct) {
    this.correct = correct;
  }

  public List<String> getAnswers() {
    return answers;
  }

  public void setAnswers(List<String> answers) {
    this.answers = answers;
  }
}
