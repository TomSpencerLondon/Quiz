package com.example.quiz.adapter.out.web.humanreadable;

import com.example.quiz.application.port.IdGenerator;
import com.github.kkuegler.HumanReadableIdGenerator;
import com.github.kkuegler.PermutationBasedHumanReadableIdGenerator;

public class ReadableIdGenerator implements IdGenerator {
    private final HumanReadableIdGenerator idGen = new PermutationBasedHumanReadableIdGenerator();

    @Override
    public String newId() {
        return idGen.generate();
    }
}
