package com.faizal.springrecipe.services;

import com.faizal.springrecipe.commands.RecipeCommand;
import com.faizal.springrecipe.domain.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

	Flux<Recipe> findAll();

	Mono<Recipe> findById(String id);

	Mono<RecipeCommand> findCommandById(String id);

	Mono<RecipeCommand> save(RecipeCommand command);

	void deleteById(String idToDelete);
}
