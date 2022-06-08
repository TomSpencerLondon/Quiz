package com.example.quiz.adapter.out.jpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "responses")
public class ResponseDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ElementCollection
    @CollectionTable(name = "response_choices",
            joinColumns = @JoinColumn(name = "response_id", referencedColumnName = "id")
    )
    @Column(name = "choice_id", nullable = false)
    Set<Long> choiceIds = new HashSet<>();

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Set<Long> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(Set<Long> choiceIds) {
        this.choiceIds = choiceIds;
    }
}
