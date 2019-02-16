package com.faizal.springrecipe.services;

import com.faizal.springrecipe.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();
}
