package com.nathan.sbecommerce.payload.response;

import com.nathan.sbecommerce.payload.request.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRes {
    private List<CategoryDTO> content;
}
