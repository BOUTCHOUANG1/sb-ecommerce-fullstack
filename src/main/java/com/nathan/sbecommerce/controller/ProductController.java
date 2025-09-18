package com.nathan.sbecommerce.controller;

import com.nathan.sbecommerce.config.AppConstant;
import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;
import com.nathan.sbecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductRequest> addProduct(@RequestBody @Valid ProductRequest productRequest,
                                                     @PathVariable Long categoryId){
        return new ResponseEntity<>(productService.addProduct(productRequest, categoryId),
                HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber",
                                                                      defaultValue = AppConstant.PAGE_NUMBER,
                                                                      required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize",
                                                                  defaultValue = AppConstant.PAGE_SIZE,
                                                                  required = false) Integer pageSize,
                                                          @RequestParam(name = "sortBy",
                                                                  defaultValue = AppConstant.SORT_PRODUCT_BY,
                                                                  required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder",
                                                                  defaultValue = AppConstant.SORT_DIR,
                                                                  required = false) String sortOrder){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber",
                                                                              defaultValue = AppConstant.PAGE_NUMBER,
                                                                              required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize",
                                                                              defaultValue = AppConstant.PAGE_SIZE,
                                                                              required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy",
                                                                              defaultValue = AppConstant.SORT_PRODUCT_BY,
                                                                              required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder",
                                                                              defaultValue = AppConstant.SORT_DIR,
                                                                              required = false) String sortOrder){
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                 @RequestParam(name = "pageNumber",
                                                                              defaultValue = AppConstant.PAGE_NUMBER,
                                                                              required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize",
                                                                              defaultValue = AppConstant.PAGE_SIZE,
                                                                              required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy",
                                                                              defaultValue = AppConstant.SORT_PRODUCT_BY,
                                                                        required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder",
                                                                        defaultValue = AppConstant.SORT_DIR,
                                                                        required = false) String sortOrder){
        ProductResponse foundProductResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(foundProductResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductRequest> updateProduct(@RequestBody @Valid ProductRequest productRequest, @PathVariable Long productId){
        ProductRequest updatedProductRequest = productService.updateProduct(productRequest, productId);
        return new ResponseEntity<>(updatedProductRequest, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductRequest> deleteProduct(@PathVariable Long productId){
        ProductRequest deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductRequest> updateProductImage(@PathVariable Long productId,
                                                             @RequestParam("image") MultipartFile image) throws IOException {
        ProductRequest updatedProductRequest = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProductRequest, HttpStatus.OK);
    }


}
