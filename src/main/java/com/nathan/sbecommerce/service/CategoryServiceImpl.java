package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories = new ArrayList<>();
    private CategoryRepository categoryRepository;

    private Long id = 1L;
    @Override
    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryid(id++);
        this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category categoryToUpdate = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Category is not found with id " + categoryId));

        category.setCategoryid(categoryId);

        Category savedCategory = this.categoryRepository.save(category);

        return savedCategory;

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category optionalCategory = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + categoryId));

        this.categoryRepository.delete(optionalCategory);

        return "Category with id " + categoryId + " is deleted successfully";

    }
}
