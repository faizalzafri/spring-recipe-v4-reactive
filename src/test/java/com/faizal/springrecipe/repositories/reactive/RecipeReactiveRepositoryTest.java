package com.faizal.springrecipe.repositories.reactive;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.faizal.springrecipe.domain.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

	@Autowired
	RecipeReactiveRepository rep;

	@Before
	public void setUp() throws Exception {
		rep.deleteAll().block();
	}

	@Test
	public void testSave() {
		// given
		Recipe recipe = new Recipe();
		recipe.setId("1");

		// when
		rep.save(recipe).block();
		Long count = rep.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);

	}

}
