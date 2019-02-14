package com.faizal.springrecipe.repositories.reactive;

import java.util.Optional;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.faizal.springrecipe.domain.Category;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

	Optional<Category> findByDescription(String description);
}
