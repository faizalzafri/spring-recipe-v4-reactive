package com.faizal.springrecipe.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.faizal.springrecipe.domain.Recipe;
import com.faizal.springrecipe.repositories.reactive.RecipeReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeReactiveRepository recipeReactiveRepository;

	public ImageServiceImpl(RecipeReactiveRepository reactiveRepository) {

		this.recipeReactiveRepository = reactiveRepository;
	}

	@Override
	public Mono<Void> save(String recipeId, MultipartFile file) {
		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId).map(recipe -> {
			Byte[] byteObjects = new Byte[0];
			try {
				byteObjects = new Byte[file.getBytes().length];

				int i = 0;

				for (byte b : file.getBytes()) {
					byteObjects[i++] = b;
				}

				recipe.setImage(byteObjects);

				return recipe;

			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		recipeReactiveRepository.save(recipeMono.block()).block();

		return Mono.empty();

	}

}
