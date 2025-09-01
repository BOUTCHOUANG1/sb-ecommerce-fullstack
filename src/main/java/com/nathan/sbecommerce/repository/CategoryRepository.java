package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank(message = "Category name is required") @Size(min = 5, message = "Category name must be at least 5 characters long") String categoryName);
}
