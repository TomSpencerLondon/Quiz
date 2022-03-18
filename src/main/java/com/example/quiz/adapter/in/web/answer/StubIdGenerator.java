package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.application.port.IdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class StubIdGenerator implements IdGenerator {
    private final AtomicInteger sequence = new AtomicInteger(1);

    @Override
    public String newId() {
        return "stub-id-" + sequence.getAndIncrement();
    }
}
