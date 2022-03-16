package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.port.IdGenerator;

public class StubIdGenerator implements IdGenerator {
    @Override
    public String newId() {
        return "stub-id-1";
    }
}
