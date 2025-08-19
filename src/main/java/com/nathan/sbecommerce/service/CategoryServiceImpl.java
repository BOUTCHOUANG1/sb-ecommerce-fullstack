package com.nathan.sbecommerce.service;

import com.nathan.sbecommerce.exception.APIException;
import com.nathan.sbecommerce.exception.ResourceNotFoundException;
import com.nathan.sbecommerce.model.Category;
import com.nathan.sbecommerce.payload.request.CategoryDTO;
import com.nathan.sbecommerce.payload.response.CategoryRes;
import com.nathan.sbecommerce.repository.CategoryRepository;
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
    public CategoryRes getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page categoryPage = this.categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }

        List<CategoryDTO> categoryDTOS =  categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryRes categoryRes = new CategoryRes();
        categoryRes.setContent(categoryDTOS);
        categoryRes.setPageNumber(categoryPage.getNumber());
        categoryRes.setPageSize(categoryPage.getSize());
        categoryRes.setTotalElements(categoryPage.getTotalElements());
        categoryRes.setTotalPages(categoryPage.getTotalPages());
        categoryRes.setLastPage(categoryPage.isLast());
        return categoryRes;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDB != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists !!!");
        }
        Category savedCategory = this.categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId) {

       Category category = modelMapper.map(categoryDto, Category.class);

        Category categoryToUpdateFromDB = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setCategoryId(categoryId);
        this.categoryRepository.save(category);
        return modelMapper.map(categoryToUpdateFromDB, CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category optionalCategory = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        this.categoryRepository.delete(optionalCategory);
        return modelMapper.map(optionalCategory, CategoryDTO.class);
    }
}
