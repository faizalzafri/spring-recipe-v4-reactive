package com.faizal.springrecipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faizal.springrecipe.commands.UnitOfMeasureCommand;
import com.faizal.springrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.faizal.springrecipe.domain.UnitOfMeasure;
import com.faizal.springrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService service;

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void listAllUoms() throws Exception {
		// given
		// Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");
		// unitOfMeasures.add(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		// unitOfMeasures.add(uom2);

		when(unitOfMeasureRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

		// when
		List<UnitOfMeasureCommand> commands = service.listAllUoms().collectList().block();

		// then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}