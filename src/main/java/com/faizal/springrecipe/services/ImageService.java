package com.faizal.springrecipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void save(String recipeId, MultipartFile file);
}
