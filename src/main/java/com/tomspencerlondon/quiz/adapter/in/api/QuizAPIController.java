package com.tomspencerlondon.quiz.adapter.in.api;

import com.tomspencerlondon.quiz.hexagon.application.CreateQuestionService;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<QuestionResponse> getAllQuestions() {
        final List<Question> questions = createQuestionService.findAll();
        return questions.stream().map(QuestionResponse::of).toList();
    }

    @PostMapping("/api/questions")
    public ResponseEntity<Object> createQuestion(AddQuestionRequest addQuestionRequest) {
        System.out.println(addQuestionRequest);
        return ResponseEntity.status(201).build();
    }
}
