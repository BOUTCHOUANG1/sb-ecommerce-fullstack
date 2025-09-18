package com.nathan.sbecommerce.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nathan.sbecommerce.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * UserDetailsImpl is a custom implementation of Spring Security's UserDetails interface.
 * This class serves as a bridge between our application's user model and Spring Security's user representation.
 *
 * Key purposes:
 * 1. Security Integration: Provides essential user information for Spring Security authentication and authorization
 * 2. User Data Encapsulation: Wraps core user attributes (id, username, email, password, authorities)
 * 3. Role-based Access Control: Manages user authorities/roles for authorization
 *
 * Use cases:
 * - Authentication: Used during login process to validate user credentials
 * - Authorization: Determines user permissions and access rights
 * - Session Management: Maintains user security context during active sessions
 *
 * Class annotations:
 * @NoArgsConstructor - Provides default constructor required by some frameworks
 * @Data - Lombok annotation generating getters, setters, toString, etc.
 * @Service - Marks this as a Spring service component
 */
@NoArgsConstructor
@Data
@Service
public class UserDetailsImpl implements UserDetails {

    /**
     * Unique identifier for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the user
     */
    private Long id;

    /**
     * User's login username
     */
    private String username;

    /**
     * User's email address
     */
    private String email;

    /**
     * User's password - marked with @JsonIgnore to prevent serialization
     */
    @JsonIgnore
    private String password;

    /**
     * Collection of user's granted authorities/roles
     */
    private Collection<? extends  GrantedAuthority> authorities;

    /**
     * Full constructor for creating a new UserDetailsImpl instance
     *
     * @param id Unique identifier for the user
     * @param username User's login username
     * @param email User's email address
     * @param password User's encrypted password
     * @param authorities Collection of user's granted authorities/roles
     */
    public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    /**
     * Builds a UserDetailsImpl instance from a Users entity
     *
     * @param user Users entity containing user information
     * @return UserDetailsImpl instance with user details
     */

    public static UserDetailsImpl build(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(R -> new SimpleGrantedAuthority(R.getAppRole().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id);
    }
}
