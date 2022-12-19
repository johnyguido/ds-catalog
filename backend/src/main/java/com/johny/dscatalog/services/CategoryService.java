package com.johny.dscatalog.services;

import com.johny.dscatalog.dto.CategoryDTO;
import com.johny.dscatalog.entities.Category;
import com.johny.dscatalog.repositories.CategoryRepository;
import com.johny.dscatalog.services.exceptions.DatabaseException;
import com.johny.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    public CategoryDTO findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        Category category = optionalCategory
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {

        Category category = Category.builder().name(categoryDTO.getName()).build();

        Category categorySave = categoryRepository.save(category);

        return new CategoryDTO(categorySave);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        try {

            Category category = categoryRepository.getOne(id);
            category.setName(categoryDTO.getName());
            category = categoryRepository.save(category);

            return new CategoryDTO(category);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Categoria não encontrada " + id);
        }

    }

    public void delete(Long id) {
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("ID não encontrado " + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integerity violation");
        }
    }
}
