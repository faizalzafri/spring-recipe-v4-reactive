package com.faizal.springrecipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faizal.springrecipe.commands.IngredientCommand;
import com.faizal.springrecipe.commands.UnitOfMeasureCommand;
import com.faizal.springrecipe.converters.IngredientCommandToIngredient;
import com.faizal.springrecipe.converters.IngredientToIngredientCommand;
import com.faizal.springrecipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.faizal.springrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.faizal.springrecipe.domain.Ingredient;
import com.faizal.springrecipe.domain.Recipe;
import com.faizal.springrecipe.repositories.RecipeRepository;
import com.faizal.springrecipe.repositories.reactive.RecipeReactiveRepository;
import com.faizal.springrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Mono;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	IngredientService ingredientService;

	// init converters
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
				new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
				recipeReactiveRepository, unitOfMeasureRepository, recipeRepository);
	}

	@Test
	public void findByRecipeIdAndId() throws Exception {
	}

	@Test
	public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setId("1");

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId("1");

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId("1");

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId("3");

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		// then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

		// when
		assertEquals("3", ingredientCommand.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
	}

	@Test
	public void testSaveRecipeCommand() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		command.setUom(new UnitOfMeasureCommand());
		command.getUom().setId("1234");

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");

		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

		// when
		IngredientCommand savedCommand = ingredientService.save(command).block();

		// then
		assertEquals("3", savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

	}

	@Test
	public void testDeleteById() throws Exception {
		// given
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId("3");
		recipe.addIngredient(ingredient);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

		// when
		ingredientService.deleteById("1", "3");

		// then
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
}