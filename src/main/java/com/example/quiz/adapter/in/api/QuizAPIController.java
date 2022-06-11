package com.example.quiz.adapter.in.api;

import com.example.quiz.adapter.in.web.edit.*;
import com.example.quiz.application.CreateQuestionService;
import com.example.quiz.domain.Question;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class QuizAPIController {
    private final CreateQuestionService createQuestionService;

    @Autowired
    public QuizAPIController(CreateQuestionService createQuestionService) {
        this.createQuestionService = createQuestionService;
    }

    @GetMapping("/api/questions")
    public List<QuestionView> getAllQuestions() {
        final List<Question> questions = createQuestionService.findAll();

        final List<QuestionView> questionViews = questions.stream().map(QuestionView::of).toList();

        return questionViews;
    }

    @PostMapping("/api/questions")
    public ResponseEntity<Object> createQuestion(@RequestBody @Valid AddQuestionForm addQuestionForm) {
            // Transform addQuestionForm to String, numbers and domain objects
            // using new adapter.in.web.QuestionService
            // then use add in createQuestionService
            QuestionService questionService = new QuestionService();
            Question question = questionService.transform(addQuestionForm);
            createQuestionService.add(question);
            return ResponseEntity.status(201).build();
    }
}
