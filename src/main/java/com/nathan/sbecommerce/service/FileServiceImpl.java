package com.nathan.sbecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    /**
     * Handles the upload of an image file to the specified directory.
     *
     * This method performs the following operations:
     * 1. Generates a unique filename using UUID to prevent filename collisions
     * 2. Creates the target directory if it doesn't exist
     * 3. Copies the uploaded file to the target location
     *
     * @param path The target directory path where the image will be stored
     * @param image The MultipartFile object containing the uploaded image
     * @return String The generated unique filename of the saved image
     * @throws IOException If there are issues during file operations
     *
     * Use cases:
     * - Product image uploads in e-commerce systems
     * - User profile picture uploads
     * - Document management systems
     * - Any scenario requiring secure file uploads with unique names
     *
     * The method ensures:
     * - File name uniqueness through UUID generation
     * - Preservation of original file extension
     * - Creation of storage directory if not present
     * - Safe file transfer to the target location
     *
     * Example usage:
     * String filename = uploadImage("uploads/products/", multipartImageFile);
     */
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String originalImageName = image.getOriginalFilename();

        String randomImageName = UUID.randomUUID().toString();

        String fileName = randomImageName.concat(originalImageName.substring(originalImageName.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        File folder = new File(path);

        if(!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(image.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
