package com.example.quiz.adapter.in.api;

import com.example.quiz.adapter.in.web.edit.AddQuestionForm;
import com.example.quiz.adapter.in.web.edit.QuestionView;
import com.example.quiz.hexagon.application.CreateQuestionService;
import com.example.quiz.hexagon.domain.Question;
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
    public List<QuestionView> getAllQuestions() {
        final List<Question> questions = createQuestionService.findAll();

        final List<QuestionView> questionViews = questions.stream().map(QuestionView::of).toList();

        return questionViews;
    }

    @PostMapping("/api/questions")
    public ResponseEntity<Object> createQuestion(AddQuestionForm addQuestionForm) {
        System.out.println(addQuestionForm);
        return ResponseEntity.status(201).build();
    }
}
