package com.johny.dscatalog.services;

import com.johny.dscatalog.dto.CategoryDTO;
import com.johny.dscatalog.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category))
                .collect(Collectors.toList());
    }

}
