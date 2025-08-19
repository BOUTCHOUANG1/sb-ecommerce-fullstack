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
    public String updateCategory(Category category, Long Categoryid) {
        List<Category> categories = this.categoryRepository.findAll();

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
        List<Category> categories = this.categoryRepository.findAll();

        Category category = categories.stream()
                .filter(c -> c.getCategoryid().equals(Categoryid))
                .findFirst().orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + Categoryid));

        categories.remove(category);
        return "Category with id " + Categoryid + " is deleted successfully";
    }
}
