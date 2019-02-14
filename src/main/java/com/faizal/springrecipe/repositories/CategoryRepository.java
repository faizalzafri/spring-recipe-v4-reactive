package com.faizal.springrecipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.faizal.springrecipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {

	Optional<Category> findByDescription(String description);
}
