package com.inventory.backend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseRest extends ResponseRest{
    
    private CategoryResponse categoryResponse = new CategoryResponse();
}
