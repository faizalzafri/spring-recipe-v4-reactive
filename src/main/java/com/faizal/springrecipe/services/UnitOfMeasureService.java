package com.faizal.springrecipe.services;

import java.util.Set;

import com.faizal.springrecipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
}
