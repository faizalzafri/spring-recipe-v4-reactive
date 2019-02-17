package com.faizal.springrecipe.services;

import org.springframework.stereotype.Service;

import com.faizal.springrecipe.commands.RecipeCommand;
import com.faizal.springrecipe.converters.RecipeCommandToRecipe;
import com.faizal.springrecipe.converters.RecipeToRecipeCommand;
import com.faizal.springrecipe.domain.Recipe;
import com.faizal.springrecipe.repositories.reactive.RecipeReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
			RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> findAll() {
		log.debug("I'm in the service finding all recipes ");
		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository.findById(id);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {

		/*RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());

		if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
			recipeCommand.getIngredients().forEach(ingredient -> {
				ingredient.setRecipeId(recipeCommand.getId());
			});
		}*/

		return recipeReactiveRepository.findById(id)
				.map(recipe -> {
					
					RecipeCommand command = recipeToRecipeCommand.convert(recipe);
					
					command.getIngredients().forEach(ingredient -> {
						
						ingredient.setRecipeId(command.getId());
				});
					return command;
			});
	}

	@Override
	public Mono<RecipeCommand> save(RecipeCommand command) {

		return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
				.map(recipeToRecipeCommand::convert);
		
	}

	@Override
	public void deleteById(String idToDelete) {
		recipeReactiveRepository.deleteById(idToDelete).block();
	}
}
