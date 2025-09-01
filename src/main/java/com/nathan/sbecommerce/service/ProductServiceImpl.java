package com.nathan.sbecommerce.service;


import com.nathan.sbecommerce.dto.request.ProductRequest;
import com.nathan.sbecommerce.dto.response.ProductResponse;
import com.nathan.sbecommerce.exception.APIException;
import com.nathan.sbecommerce.exception.ResourceNotFoundException;
import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.model.Product;
import com.nathan.sbecommerce.repository.CategoryRepository;
import com.nathan.sbecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductRequest addProduct(ProductRequest productRequest,
                                     Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isProductNotPresent = true;

        List<Product> products = category.getProducts();

        for(Product product : products){
            if(product.getProductName().equals(productRequest.getProductName())){
                isProductNotPresent = false;
                break;
            }
        }
        if(!isProductNotPresent){
            Product product = modelMapper.map(productRequest, Product.class);

            product.setCategory(category);
            product.setImage("default.png");
            Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = this.productRepository.save(product);
            return modelMapper.map(savedProduct, ProductRequest.class);
        } else {
            throw new APIException("Product already exists");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber,
                                          Integer pageSize,
                                          String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page productPage = this.productRepository.findAll(pageable);

        List<Product> products = productPage.getContent();

        if(products.isEmpty()){
            throw new APIException("No products found");
        }

        List<ProductRequest> productRequests = products.stream()
                .map(prod -> modelMapper.map(prod, ProductRequest.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductRequests(productRequests);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


        List<Product> productPage = this.productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductRequest> productRequests = productPage.stream()
                .map(prod -> modelMapper.map(prod, ProductRequest.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductRequests(productRequests);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> productPage = this.productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");
        if(productPage.isEmpty()){
            throw new APIException("No products found with keyword: " + keyword);
        }

        List<ProductRequest> productRequests = productPage.stream()
                .map(prod -> modelMapper.map(prod, ProductRequest.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductRequests(productRequests);
        return productResponse;
    }

    @Override
    public ProductRequest updateProduct(ProductRequest productRequest, Long productId) {
        Product productToUpdate = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productRequest, Product.class);
        //productToUpdate.setProductId(productId);
        productToUpdate.setProductName(product.getProductName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setQuantity(product.getQuantity());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setDiscount(product.getDiscount());
        productToUpdate.setSpecialPrice(product.getSpecialPrice());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate = this.productRepository.save(product);
        return modelMapper.map(productToUpdate, ProductRequest.class);
    }

    @Override
    public ProductRequest deleteProduct(Long productId) {
        Product productToDelete = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        this.productRepository.delete(productToDelete);
        return modelMapper.map(productToDelete, ProductRequest.class);
    }

/**
 * Updates the image associated with a specific product in the system.
 *
 * This method handles the process of updating/changing a product's image by:
 * 1. Finding the product by its ID in the database
 * 2. Uploading the new image to the file system
 * 3. Updating the product's image reference in the database
 *
 * @param productId The unique identifier of the product whose image needs to be updated
 * @param image The new image file to be uploaded (must be a MultipartFile)
 * @return ProductRequest object containing the updated product information
 * @throws ResourceNotFoundException if the product with given ID is not found
 * @throws IOException if there are issues during file upload/handling
 *
 * Use cases:
 * - When adding a new image to a product that previously had no image
 * - When replacing/updating an existing product image
 * - When managing product images through an admin interface
 *
 * The image will be:
 * - Stored in the "images/" directory
 * - Given a unique filename using UUID
 * - Linked to the product in the database
 */
@Override
public ProductRequest updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productToUpdate = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String fileName = this.fileService.uploadImage(path, image);

        productToUpdate.setImage(fileName);

        Product updatedProduct = this.productRepository.save(productToUpdate);
        return modelMapper.map(updatedProduct, ProductRequest.class);
    }

}
