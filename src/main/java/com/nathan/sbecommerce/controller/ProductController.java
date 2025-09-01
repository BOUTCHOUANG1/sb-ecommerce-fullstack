package com.nathan.sbecommerce.controller;

import com.nathan.sbecommerce.config.AppConstant;
import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;
import com.nathan.sbecommerce.model.Product;
import com.nathan.sbecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductRequest> addProduct(@RequestBody Product product,
                                                     @PathVariable Long categoryId){
        ProductRequest productRequest = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(productRequest, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber",
                                                                      defaultValue = AppConstant.PAGE_NUMBER,
                                                                      required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize",
                                                                  defaultValue = AppConstant.PAGE_SIZE,
                                                                  required = false) Integer pageSize,
                                                          @RequestParam(name = "sortBy",
                                                                  defaultValue = AppConstant.SORT_BY,
                                                                  required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder",
                                                                  defaultValue = AppConstant.SORT_DIR,
                                                                  required = false) String sortOrder){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }
}
