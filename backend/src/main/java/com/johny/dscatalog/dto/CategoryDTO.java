package com.johny.dscatalog.dto;

import com.johny.dscatalog.entities.Category;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    private Long id;
    private String name;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }

}
