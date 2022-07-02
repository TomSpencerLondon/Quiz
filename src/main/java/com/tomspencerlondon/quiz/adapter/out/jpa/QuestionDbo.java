package com.tomspencerlondon.quiz.adapter.out.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<ChoiceDbo> choices = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;
    
    public List<ChoiceDbo> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDbo> choices) {
        this.choices = choices;
    }

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

    public ChoiceType getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(ChoiceType choiceType) {
        this.choiceType = choiceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDbo that = (QuestionDbo) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
