package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.exception.APIException;
import com.nathan.sbecommerce.exception.ResourceNotFoundException;
import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists !!!");
        }
        this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category categoryToUpdate = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setCategoryId(categoryId);
        Category savedCategory = this.categoryRepository.save(category);
        return savedCategory;

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category optionalCategory = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        this.categoryRepository.delete(optionalCategory);
        return "Category with id " + categoryId + " is deleted successfully";
    }
}
