package com.nathan.sbecommerce.repository;

import com.nathan.sbecommerce.model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String username);

    boolean existsByUserName(@NotBlank @Size(min =  3, max = 50) String username);

    boolean existsByEmail(@NotBlank @Email @Size(max = 50) String email);
}
