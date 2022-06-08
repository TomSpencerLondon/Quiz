package com.example.quiz.adapter.out.web.humanreadable;

import com.example.quiz.application.port.TokenGenerator;
import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;

public class ReadableTokenGenerator implements TokenGenerator {
    private final HumanReadableIdGenerator idGen = new PermutationBasedHumanReadableIdGenerator();

    @Override
    public String token() {
        return idGen.generate();
    }
}
