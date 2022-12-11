package com.inventory.backend.response;

import java.util.List;

import com.inventory.backend.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
    
    private List<Category> category;
}
