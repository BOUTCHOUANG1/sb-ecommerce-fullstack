package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

/**
 * Retrieves a list of products filtered by a specific category and sorted by price in ascending order.
 *
 * This method is used to:
 * - Find all products belonging to a given category
 * - Sort those products from lowest price to highest price
 * - Return them as an ordered List
 *
 * Use cases:
 * - Displaying category-specific product listings with price sorting
 * - Showing users the cheapest products first within a category
 * - Implementing price-based filtering on category pages
 *
 * @param category The Category entity to filter products by
 * @return List<Product> A list of products in the specified category, sorted by ascending price
 */
List<Product> findByCategoryOrderByPriceAsc(Category category);

/**
 * Retrieves a list of products by performing a case-insensitive search on product names.
 *
 * This method is used to:
 * - Search for products where the name matches the given search term
 * - Ignore letter casing when matching product names
 * - Return all matching products as a List
 *
 * The search uses SQL LIKE operator, so wildcards can be included in the search term:
 * - % matches zero or more characters
 * - _ matches exactly one character
 *
 * Use cases:
 * - Implementing product search functionality
 * - Auto-complete/suggestions for product names
 * - Filtering products by name without case sensitivity
 * - Finding products when exact case is unknown
 *
 * @param productName The search term to match against product names
 * @return List<Product> A list of products whose names match the search term (case-insensitive)
 */
List<Product> findByProductNameLikeIgnoreCase(String productName);
}
