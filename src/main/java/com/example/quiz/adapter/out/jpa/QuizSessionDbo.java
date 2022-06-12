package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.QuizId;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "quiz_sessions")
public class QuizSessionDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private ZonedDateTime startedAt;
    private String token;
    Long currentQuestionId;
    private Long quizId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id")
    List<ResponseDbo> responses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(Long currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public List<ResponseDbo> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDbo> responses) {
        this.responses = responses;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizSessionDbo that = (QuizSessionDbo) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
