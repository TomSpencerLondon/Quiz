package com.example.quiz.application.port;

import com.example.quiz.domain.Response;

public interface ResponseRepository {
    Response save(Response response);
}
