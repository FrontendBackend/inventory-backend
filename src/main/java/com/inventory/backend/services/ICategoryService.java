package com.inventory.backend.services;

import org.springframework.http.ResponseEntity;

import com.inventory.backend.model.Category;
import com.inventory.backend.response.CategoryResponseRest;

public interface ICategoryService {
    
    public ResponseEntity<CategoryResponseRest> search();

    public ResponseEntity<CategoryResponseRest> searchById(Long id);

    public ResponseEntity<CategoryResponseRest> save(Category category);

    public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
	
    public ResponseEntity<CategoryResponseRest> deleteById(Long id);
}
