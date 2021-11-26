package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/quiz")
public class AddQuestion {

  private final QuestionService questionService;

  @Autowired
  public AddQuestion(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping
  public ResponseEntity<Question> createEpic(@RequestBody EpicDto epicDto) {

    Epic newEpic = epicService.createNewEpic(epicConverter.toEntity(epicDto));

    return new ResponseEntity(epicConverter.toDto(newEpic), HttpStatus.OK);
  }
}
