package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.hexagon.application.port.TokenGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class StubTokenGenerator implements TokenGenerator {
    private final AtomicInteger sequence = new AtomicInteger(1);

    @Override
    public String token() {
        return "stub-id-" + sequence.getAndIncrement();
    }
}
