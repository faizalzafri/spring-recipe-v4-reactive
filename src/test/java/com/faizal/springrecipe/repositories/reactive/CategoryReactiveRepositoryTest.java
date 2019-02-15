package com.faizal.springrecipe.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.faizal.springrecipe.domain.Category;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	private static final String CATEGORY = "Integration Test Category";

	@Autowired
	CategoryReactiveRepository rep;

	@Before
	public void setUp() throws Exception {
		rep.deleteAll().block();
	}

	@Test
	public void testSave() {
		// given
		Category category = new Category();
		category.setId("1");

		// when
		rep.save(category).block();
		Long count = rep.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);

	}

	@Test
	public void testFindByDescription() {
		// given
		Category category = new Category();
		category.setId("1");

		Category category2 = new Category();
		category2.setId("2");
		category2.setDescription(CATEGORY);

		Set<Category> hashSet = new HashSet<>();
		hashSet.add(category);
		hashSet.add(category2);

		// when
		rep.saveAll(hashSet).blockLast().getDescription();
		Long count = rep.count().block();
		Category fetchedCategory = rep.findByDescription(CATEGORY).block();

		// then
		assertNotEquals(Long.valueOf(1L), count);
		assertNotNull(fetchedCategory.getId());

	}

}
