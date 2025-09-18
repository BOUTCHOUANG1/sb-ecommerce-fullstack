package com.nathan.sbecommerce.controller;

import com.nathan.sbecommerce.dto.request.LoginRequest;
import com.nathan.sbecommerce.dto.request.SignupRequest;
import com.nathan.sbecommerce.dto.response.LoginResponse;
import com.nathan.sbecommerce.dto.response.MessageResponse;
import com.nathan.sbecommerce.exception.APIException;
import com.nathan.sbecommerce.model.AppRole;
import com.nathan.sbecommerce.model.Roles;
import com.nathan.sbecommerce.model.Users;
import com.nathan.sbecommerce.repository.RoleRepository;
import com.nathan.sbecommerce.repository.UserRepository;
import com.nathan.sbecommerce.security.JwtUtils;
import com.nathan.sbecommerce.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Authenticates a user and generates a JWT token
     *
     * This method handles user authentication using the provided login credentials.
     * If the authentication is successful, it generates a JWT token for the user.
     *
     * @param loginRequest The LoginRequest object containing user credentials
     * @return A ResponseEntity containing the LoginResponse with JWT token and user details
     */
    /**
     * Authenticates a user and generates a JWT token
     *
     * This method handles user authentication using the provided login credentials.
     * If the authentication is successful, it generates a JWT token for the user.
     *
     * @param loginRequest The LoginRequest object containing user credentials
     * @return A ResponseEntity containing the LoginResponse with JWT token and user details
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getId(), userDetails.getUsername(), roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtCookie.toString())
                .body(response);
    }

    /**
     * Registers a new user in the application
     *
     * This method handles user registration using the provided signup request.
     * It validates the username, email, and password, and checks if they already exist in the system.
     * If the validation passes, it creates a new user account with the provided information.
     *
     * @param signupRequest The SignupRequest object containing user registration details
     * @return A ResponseEntity containing a MessageResponse indicating the success or failure of the registration
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username is already taken"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken"));
        }

        Users user = new Users(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        Set<String> strRoles = signupRequest.getRole();

        Set<Roles> roles = new HashSet<>();

        if(strRoles == null || strRoles.isEmpty()) {
            Roles userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new APIException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Roles adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new APIException("Error: Role is not found"));
                        roles.add(adminRole);
                    }
                    case "seller" -> {
                        Roles sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                            .orElseThrow(() -> new APIException("Error: Role is not found"));
                        roles.add(sellerRole);
                    }
                    default -> {
                        Roles userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                            .orElseThrow(() -> new APIException("Error: Role is not found"));
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/current-user")
    public String currentUser(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        }
        return "";
    }

    @GetMapping("/current-user")
    public ResponseEntity<LoginResponse> getUserDetails(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getId(), userDetails.getUsername(), roles);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getClearJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
