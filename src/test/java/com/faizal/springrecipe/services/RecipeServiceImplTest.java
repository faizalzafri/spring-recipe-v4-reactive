package com.faizal.springrecipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faizal.springrecipe.commands.RecipeCommand;
import com.faizal.springrecipe.converters.RecipeCommandToRecipe;
import com.faizal.springrecipe.converters.RecipeToRecipeCommand;
import com.faizal.springrecipe.domain.Recipe;
import com.faizal.springrecipe.exceptions.NotFoundException;
import com.faizal.springrecipe.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void getRecipeByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		Recipe recipeReturned = recipeService.findById("1").block();

		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	public void getRecipeCommandByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

		RecipeCommand commandById = recipeService.findCommandById("1").block();

		assertNotNull("Null recipe returned", commandById);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	public void getRecipesTest() throws Exception {

		when(recipeService.findAll()).thenReturn(Flux.just(new Recipe()));

		List<Recipe> recipes = recipeReactiveRepository.findAll().collectList().block();

		assertEquals(recipes.size(), 1);
		verify(recipeReactiveRepository, times(1)).findAll();
		verify(recipeReactiveRepository, never()).findById(anyString());
	}

	@Test
	public void testDeleteById() throws Exception {

		// given
		String idToDelete = "2";

		// when
		when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());
		recipeService.deleteById(idToDelete);

		// then
		verify(recipeReactiveRepository, times(1)).deleteById(anyString());
	}
}