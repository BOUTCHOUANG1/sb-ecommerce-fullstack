package com.nathan.sbecommerce.service.impl;

import com.nathan.sbecommerce.model.Users;
import com.nathan.sbecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring Security's UserDetailsService interface.
 * This service class is responsible for loading user-specific data during authentication.
 *
 * Purpose:
 * - Provides core user authentication functionality for Spring Security
 * - Retrieves user details from the database
 * - Converts application-specific user model (Users) to Spring Security's UserDetails
 *
 * Use Cases:
 * - Used during login/authentication process
 * - Called by Spring Security's authentication manager
 * - Validates user existence in the system
 * - Provides user authorities/roles for authorization
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Repository interface for Users entity to handle database operations
     */
    private final UserRepository userRepository;

    /**
     * Locates the user based on the username provided during login.
     * This is the core method used by Spring Security for user authentication.
     *
     * @param username The username identifying the user whose data is required
     * @return A fully populated UserDetails object that Spring Security can use for authentication
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}

