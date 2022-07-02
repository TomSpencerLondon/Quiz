package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.Response;

public interface ResponseRepository {
    Response save(Response response);
}
