package com.tomspencerlondon.quiz.adapter.out.web.humanreadable;

import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;
import com.tomspencerlondon.quiz.hexagon.application.port.TokenGenerator;

public class ReadableTokenGenerator implements TokenGenerator {
    private final HumanReadableIdGenerator idGen = new PermutationBasedHumanReadableIdGenerator();

    @Override
    public String token() {
        return idGen.generate();
    }
}
