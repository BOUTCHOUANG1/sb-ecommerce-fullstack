package com.nathan.sbecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * The ModelMapper bean is used for mapping objects of different types. It is a powerful tool for converting
     * between different objects and classes. In this project, we use it to map DTOs (Data Transfer Objects) to
     * entities and vice versa.
     *
     * For example, when we receive a request to create a new category, we map the request body to a CategoryRequest
     * object. Then, we use the ModelMapper to map the CategoryRequest object to a Category entity. This way, we can
     * easily create a new Category entity with the data from the request.
     *
     * Another use case for the ModelMapper is when we need to map an entity to a DTO. For example, when we need to
     * return a category data to the client, we map the Category entity to a CategoryRequest object.
     *
     * It is important to note that the ModelMapper is a bean that is automatically instantiated and injected into
     * the necessary components of the application. Therefore, we do not need to manually create a new ModelMapper
     * instance. We can simply inject it into any component that needs it.
     *
     * @return a new instance of the ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
