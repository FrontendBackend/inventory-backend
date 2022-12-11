package com.inventory.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.inventory.backend.model.Category;

public interface ICategoryDao extends CrudRepository<Category, Long>{
    
}
