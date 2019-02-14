package com.faizal.springrecipe.repositories;

import org.springframework.data.repository.CrudRepository;

import com.faizal.springrecipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
