package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;


public interface ProductService {
    ProductRequest addProduct(ProductRequest product, Long categoryId);
    ProductResponse getAllProducts(Integer pageNumber,
                                         Integer pageSize,
                                         String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductRequest updateProduct(ProductRequest product, Long productId);

    ProductRequest deleteProduct(Long productId);
}
