package com.example.quiz.hexagon.application.port;

import com.example.quiz.hexagon.domain.Response;

public interface ResponseRepository {
    Response save(Response response);
}
