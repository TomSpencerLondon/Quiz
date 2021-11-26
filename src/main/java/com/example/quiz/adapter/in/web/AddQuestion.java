package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<Question> addQuestion(@RequestBody QuestionRequest questionRequest) {

    final Question question = new Question(questionRequest.getText(),
        new MultipleChoice(
            new Answer(questionRequest.getAnswer()),
            List.of(
                new Answer(questionRequest.getChoices().get(0)),
                new Answer(questionRequest.getChoices().get(1)),
                new Answer(questionRequest.getChoices().get(2)),
                new Answer(questionRequest.getChoices().get(3)))
        )
    );

    Question response = questionService.add(question);

    return new ResponseEntity(response, HttpStatus.OK);
  }
}
