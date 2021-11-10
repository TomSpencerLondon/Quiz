package com.example.quiz.adapter.port.repository.jpa;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDtoTransformer {

  @Autowired
  public QuestionDtoTransformer() {}

  Question toQuestion(QuestionDto questionDto) {
    final MultipleChoiceDto multipleChoiceDto = questionDto.getMultipleChoiceDto();
    final List<Answer> answers = multipleChoiceDto
        .getAnswers()
        .stream()
        .map(Answer::new)
        .collect(Collectors.toList());
    ;

    return new Question(
        questionDto.getText(),
        new MultipleChoice(
            new Answer(multipleChoiceDto.getCorrect()),
            answers
        ));
  }

  QuestionDto toQuestionDto(Question question) {
    final List<String> answers = question.answers().stream().map(Answer::text).toList();
    final Answer answer = question.correctAnswer();
    final String questionText = question.text();

    final MultipleChoiceDto multipleChoiceDto = new MultipleChoiceDto();
    final QuestionDto questionDto = new QuestionDto();
    multipleChoiceDto.setCorrect(answer.text());
    multipleChoiceDto.setAnswers(answers);
    questionDto.setText(questionText);
    questionDto.setMultipleChoiceDto(multipleChoiceDto);
    return questionDto;
  }
}
