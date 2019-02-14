package com.faizal.springrecipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.faizal.springrecipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
