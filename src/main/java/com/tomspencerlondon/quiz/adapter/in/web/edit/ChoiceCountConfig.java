package com.tomspencerlondon.quiz.adapter.in.web.edit;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@ConfigurationProperties(prefix = "questions")
public class ChoiceCountConfig {

    @Max(5)
    @Min(2)
    private Integer baseNumberOfChoices;

    public Integer getBaseNumberOfChoices() {
        return baseNumberOfChoices;
    }

    public void setBaseNumberOfChoices(Integer baseNumberOfChoices) {
        this.baseNumberOfChoices = baseNumberOfChoices;
    }
}
