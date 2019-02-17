package com.faizal.springrecipe.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface ImageService {

	Mono<Void> save(String recipeId, MultipartFile file);
}
