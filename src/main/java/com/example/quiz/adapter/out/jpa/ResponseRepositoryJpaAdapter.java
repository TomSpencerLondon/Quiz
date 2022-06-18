package com.example.quiz.adapter.out.jpa;

import com.example.quiz.hexagon.application.port.ResponseRepository;
import com.example.quiz.hexagon.domain.Response;

public class ResponseRepositoryJpaAdapter implements ResponseRepository {
    private final ResponseTransformer responseTransformer;
    private final ResponseJpaRepository responseJpaRepository;

    public ResponseRepositoryJpaAdapter(ResponseTransformer responseTransformer, ResponseJpaRepository responseJpaRepository) {
        this.responseTransformer = responseTransformer;
        this.responseJpaRepository = responseJpaRepository;
    }


    @Override
    public Response save(Response response) {
        ResponseDbo responseDbo = responseTransformer.toResponseDbo(response);
        return responseTransformer.toResponse(responseJpaRepository.save(responseDbo));
    }

}
