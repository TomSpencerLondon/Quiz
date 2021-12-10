package com.example.quiz.adapter.out.repository.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class QuestionDto {

  @OneToOne(cascade = CascadeType.ALL)
  private MultipleChoiceDto multipleChoiceDto;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String text;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public MultipleChoiceDto getMultipleChoiceDto() {
    return multipleChoiceDto;
  }

  public void setMultipleChoiceDto(
      MultipleChoiceDto multipleChoiceDto) {
    this.multipleChoiceDto = multipleChoiceDto;
  }
}
