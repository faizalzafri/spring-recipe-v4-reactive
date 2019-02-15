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

import com.faizal.springrecipe.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

	private static final String UOM = "Integration Test UnitOfMeasure";

	@Autowired
	UnitOfMeasureReactiveRepository rep;

	@Before
	public void setUp() throws Exception {
		rep.deleteAll().block();
	}

	@Test
	public void testSave() {
		// given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId("1");

		// when
		rep.save(uom).block();
		Long count = rep.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);

	}

	@Test
	public void testFindByDescription() {
		// given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId("1");

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		uom2.setDescription(UOM);

		Set<UnitOfMeasure> hashSet = new HashSet<>();
		hashSet.add(uom);
		hashSet.add(uom2);

		// when
		rep.saveAll(hashSet).blockLast().getDescription();
		Long count = rep.count().block();
		UnitOfMeasure fetchedCategory = rep.findByDescription(UOM).block();

		// then
		assertNotEquals(Long.valueOf(1L), count);
		assertNotNull(fetchedCategory.getId());
		assertEquals(UOM, fetchedCategory.getDescription());

	}

}
