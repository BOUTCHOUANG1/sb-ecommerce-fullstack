package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
