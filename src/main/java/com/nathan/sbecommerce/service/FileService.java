package com.nathan.sbecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileService is an interface that defines methods for file operations.
 *
 * This interface provides a contract for uploading images to a specified path.
 *
 * Key methods:
 * - uploadImage: Uploads an image file to the specified path
 *
 * Use cases:
 * - Handling image uploads in a Spring Boot application
 * - Integrating with cloud storage services for image storage
 *
 * Class annotations:
 * - @Service: Indicates that this interface is a Spring service component
 */
@Service
public interface FileService {
    String uploadImage(String path, MultipartFile image) throws IOException;
}
