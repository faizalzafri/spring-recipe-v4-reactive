package com.faizal.springrecipe.services;

import com.faizal.springrecipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

	IngredientCommand save(IngredientCommand command);

	void deleteById(String recipeId, String idToDelete);
}
