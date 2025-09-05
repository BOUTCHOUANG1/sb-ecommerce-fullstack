package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

/**
 * Retrieves a list of products filtered by a specific category and sorted by price in ascending order.
 * <p>
 * This method is used to:
 * - Find all products belonging to a given category
 * - Sort those products from lowest price to highest price
 * - Return them as an ordered List
 * <p>
 * Use cases:
 * - Displaying category-specific product listings with price sorting
 * - Showing users the cheapest products first within a category
 * - Implementing price-based filtering on category pages
 *
 * @param category The Category entity to filter products by
 * @param pageable
 * @return List<Product> A list of products in the specified category, sorted by ascending price
 */
Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageable);

/**
 * Retrieves a list of products by performing a case-insensitive search on product names.
 * <p>
 * This method is used to:
 * - Search for products where the name matches the given search term
 * - Ignore letter casing when matching product names
 * - Return all matching products as a List
 * <p>
 * The search uses SQL LIKE operator, so wildcards can be included in the search term:
 * - % matches zero or more characters
 * - _ matches exactly one character
 * <p>
 * Use cases:
 * - Implementing product search functionality
 * - Auto-complete/suggestions for product names
 * - Filtering products by name without case sensitivity
 * - Finding products when exact case is unknown
 *
 * @param productName The search term to match against product names
 * @param pageable
 * @return List<Product> A list of products whose names match the search term (case-insensitive)
 */
Page<Product> findByProductNameLikeIgnoreCase(String productName, Pageable pageable);
}
