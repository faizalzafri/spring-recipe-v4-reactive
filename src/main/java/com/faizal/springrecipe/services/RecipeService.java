package com.faizal.springrecipe.services;

import java.util.Set;

import com.faizal.springrecipe.commands.RecipeCommand;
import com.faizal.springrecipe.domain.Recipe;

public interface RecipeService {

	Set<Recipe> findAll();

	Recipe findById(String id);

	RecipeCommand findCommandById(String id);

	RecipeCommand save(RecipeCommand command);

	void deleteById(String idToDelete);
}
