package com.nathan.sbecommerce.service.impl;

import com.nathan.sbecommerce.dto.request.CategoryRequest;
import com.nathan.sbecommerce.exception.APIException;
import com.nathan.sbecommerce.exception.ResourceNotFoundException;
import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.dto.response.CategoryResponse;
import com.nathan.sbecommerce.repository.CategoryRepository;
import com.nathan.sbecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getCategories(Integer pageNumber,
                                          Integer pageSize,
                                          String sortBy,
                                          String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page categoryPage = this.categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }

        List<CategoryRequest> categoryRequests =  categories.stream()
                .map(category -> modelMapper.map(category, CategoryRequest.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryRequests);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    /**
     * This method is used to create a new category in the database. It takes a CategoryRequest object as a parameter,
     * which contains the details of the category to be created. It uses the ModelMapper to map the CategoryRequest object
     * to a Category object. It then checks if a category with the same name already exists in the database. If it does,
     * it throws an APIException. If not, it saves the category to the database and returns a CategoryRequest object
     * containing the details of the newly created category.
     *
     * @param categoryRequest the CategoryRequest object containing the details of the category to be created.
     * @return a CategoryRequest object containing the details of the newly created category.
     * @throws APIException if a category with the same name already exists in the database.
     */
    public CategoryRequest createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDB != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists !!!");
        }
        Category savedCategory = this.categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryRequest.class);
    }

    @Override
    /**
     * This method is used to update an existing category in the database. It takes a CategoryRequest object as a parameter,
     * which contains the updated details of the category. It also takes a categoryId as a parameter, which is the identifier of the
     * category to be updated. It uses the ModelMapper to map the CategoryRequest object to a Category object.
     * It then retrieves the category from the database using the categoryId. If the category does not exist, it throws a ResourceNotFoundException.
     * It then updates the category in the database with the new details and returns a CategoryRequest object containing the updated details of the category.
     *
     * This method is used in cases when an existing category needs to be updated. It is particularly useful when an admin user wants to update
     * the details of a category, such as the category name.
     *
     * @param categoryRequest the CategoryRequest object containing the updated details of the category.
     * @param categoryId the identifier of the category to be updated.
     * @return a CategoryRequest object containing the updated details of the category.
     * @throws ResourceNotFoundException if the category with the provided categoryId does not exist in the database.
     */
    public CategoryRequest updateCategory(CategoryRequest categoryRequest, Long categoryId) {

       Category category = modelMapper.map(categoryRequest, Category.class);

        Category categoryToUpdateFromDB = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setCategoryId(categoryId);
        this.categoryRepository.save(category);
        return modelMapper.map(categoryToUpdateFromDB, CategoryRequest.class);

    }

    @Override
    /**
     * This method is used to delete a category from the database. It takes a categoryId as a parameter,
     * which is the identifier of the category to be deleted. It retrieves the category from the database
     * using the categoryId. If the category does not exist, it throws a ResourceNotFoundException.
     * It then deletes the category from the database and returns a CategoryRequest object containing the details
     * of the deleted category.
     *
     * This method is used in cases when an admin user wants to delete a category from the database. It is particularly
     * useful when an admin user wants to remove a category from the e-commerce website.
     *
     * @param categoryId the identifier of the category to be deleted.
     * @return a CategoryRequest object containing the details of the deleted category.
     * @throws ResourceNotFoundException if the category with the provided categoryId does not exist in the database.
     */
    public CategoryRequest deleteCategory(Long categoryId) {
        Category optionalCategory = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        this.categoryRepository.delete(optionalCategory);
        return modelMapper.map(optionalCategory, CategoryRequest.class);
    }
}
