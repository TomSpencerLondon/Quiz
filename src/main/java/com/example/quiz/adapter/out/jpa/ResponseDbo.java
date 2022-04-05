package com.example.quiz.adapter.out.jpa;

import javax.persistence.*;

@Entity
@Table(name = "responses")
public class ResponseDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
