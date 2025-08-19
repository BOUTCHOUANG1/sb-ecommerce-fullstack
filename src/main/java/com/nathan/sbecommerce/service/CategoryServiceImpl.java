package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long id = 1L;
    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryid(id++);
        this.categories.add(category);
    }

    @Override
    public String updateCategory(Category category) {
        Optional<Category> optionalCategory= categories.stream()
                .filter(c -> c.getCategoryid().equals(category.getCategoryid()))
                .findFirst();;

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryname(category.getCategoryname());
            return existingCategory.getCategoryname() + " is updated successfully";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + category.getCategoryid());
        }
    }

    @Override
    public String deleteCategory(Long Categoryid) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryid().equals(Categoryid))
                .findFirst().orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + Categoryid));

        categories.remove(category);
        return "Category with id " + Categoryid + " is deleted successfully";
    }
}
