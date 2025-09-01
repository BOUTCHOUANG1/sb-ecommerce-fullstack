package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;
import com.nathan.sbecommerce.model.Product;


public interface ProductService {
    ProductRequest addProduct(Product product, Long categoryId);
    ProductResponse getAllProducts(Integer pageNumber,
                                         Integer pageSize,
                                         String sortBy, String sortOrder);
}
