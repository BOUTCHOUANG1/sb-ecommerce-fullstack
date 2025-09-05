package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    ProductRequest addProduct(ProductRequest product, Long categoryId);
    ProductResponse getAllProducts(Integer pageNumber,
                                         Integer pageSize,
                                         String sortBy,
                                   String sortOrder);

    ProductResponse searchByCategory(Long categoryId,
                                     Integer pageNumber,
                                     Integer pageSize,
                                     String sortBy,
                                     String sortOrder);

    ProductResponse searchProductByKeyword(String keyword,
                                           Integer pageNumber,
                                           Integer pageSize,
                                           String sortBy,
                                           String sortOrder);

    ProductRequest updateProduct(ProductRequest product, Long productId);

    ProductRequest deleteProduct(Long productId);

    ProductRequest updateProductImage(Long productId, MultipartFile image) throws IOException;
}
