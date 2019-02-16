package com.faizal.springrecipe.services;

import com.faizal.springrecipe.commands.IngredientCommand;

import reactor.core.publisher.Mono;

public interface IngredientService {

	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

	Mono<IngredientCommand> save(IngredientCommand command);

	Mono<Void> deleteById(String recipeId, String idToDelete);
}
