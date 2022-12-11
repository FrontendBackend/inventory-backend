package com.inventory.backend.services;

import org.springframework.http.ResponseEntity;

import com.inventory.backend.response.CategoryResponseRest;

public interface ICategoryService {
    
    public ResponseEntity<CategoryResponseRest> search();
	
}
