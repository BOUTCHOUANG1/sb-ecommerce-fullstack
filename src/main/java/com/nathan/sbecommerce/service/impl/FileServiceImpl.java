package com.nathan.sbecommerce.service.impl;

import com.nathan.sbecommerce.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    /**
     * Handles the upload of an image file to the specified directory.
     * <p>
     * This method performs the following operations:
     * 1. Generates a unique filename using UUID to prevent filename collisions
     * 2. Creates the target directory if it doesn't exist
     * 3. Copies the uploaded file to the target location
     *
     * @param path  The target directory path where the image will be stored
     * @param image The MultipartFile object containing the uploaded image
     * @return String The generated unique filename of the saved image
     * @throws IOException If there are issues during file operations
     *                     <p>
     *                     Use cases:
     *                     - Product image uploads in e-commerce systems
     *                     - User profile picture uploads
     *                     - Document management systems
     *                     - Any scenario requiring secure file uploads with unique names
     *                     <p>
     *                     The method ensures:
     *                     - File name uniqueness through UUID generation
     *                     - Preservation of original file extension
     *                     - Creation of storage directory if not present
     *                     - Safe file transfer to the target location
     *                     <p>
     *                     Example usage:
     *                     String filename = uploadImage("uploads/products/", multipartImageFile);
     */
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        // Get the original name of the uploaded image file (e.g. "photo.jpg")
        String originalImageName = image.getOriginalFilename();

        // Generate a random unique ID to use as the new filename to avoid conflicts
        String randomImageName = UUID.randomUUID().toString();

        // Create new filename by combining random ID with original file extension
        // e.g. "123e4567-e89b-12d3-a456-426614174000.jpg"
        String fileName = randomImageName.concat(originalImageName.substring
                (originalImageName.lastIndexOf(".")));

        // Build the complete file path by combining target directory path and new filename
        String filePath = path + File.separator + fileName;

        // Create a File object representing the target directory
        File folder = new File(path);

        // Create the target directory if it doesn't exist yet
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Copy the uploaded image file to the target location with the new filename
        Files.copy(image.getInputStream(), Paths.get(filePath));

        // Return the new unique filename that was generated
        return fileName;
    }
}
