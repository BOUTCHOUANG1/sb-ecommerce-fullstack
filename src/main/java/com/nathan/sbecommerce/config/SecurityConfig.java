package com.nathan.sbecommerce.config;

import com.nathan.sbecommerce.security.AuthEntryPointJwt;
import com.nathan.sbecommerce.security.AuthTokenFilter;
import com.nathan.sbecommerce.security.JwtUtils;
import com.nathan.sbecommerce.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;

    /**
     * Configures the authentication token filter for the application
     *
     * This method sets up the authentication token filter to be used by Spring Security.
     * It configures the JWT utils and user details service for token validation and user authentication.
     *
     * @return The configured AuthTokenFilter
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager for the application
     *
     * This method sets up the authentication manager to be used by Spring Security.
     * It configures the authentication provider for user authentication.
     *
     * @param authConfig The AuthenticationConfiguration object for configuring authentication
     * @return The configured AuthenticationManager
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configures the authentication provider for the application
     *
     * This method sets up the authentication provider to be used by Spring Security.
     * It configures the user details service and password encoder for user authentication.
     *
     * @return The configured DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    /**
     * Configures the security filter chain for HTTP requests
     *
     * This method sets up the security rules for the application, including:
     * - Disabling CSRF protection
     * - Configuring authorization rules for different request patterns
     * - Setting up session management to be stateless
     * - Configuring authentication providers and entry points
     *
     * Key features:
     * - Permits access to public endpoints (authentication, Swagger UI, API docs)
     * - Requires authentication for all other endpoints
     * - Stateless session management for RESTful APIs
     * - Custom authentication entry point for unauthorized access
     *
     * @param http The HttpSecurity object for configuring security
     * @return The configured SecurityFilterChain
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
        );

        /**
         * Configures the authentication provider for the application
         *
         * This method sets up the authentication provider to be used by Spring Security.
         * It configures the user details service and password encoder for user authentication.
         *
         * @param authenticationProvider The authentication provider to be configured
         */
        http.authenticationProvider(authenticationProvider());

        /**
         * Configures the exception handling for the application
         *
         * This method sets up the exception handling mechanism for the application.
         * It configures the authentication entry point to be used when an unauthenticated
         * user tries to access a protected resource.
         *
         * @param exception The HttpSecurity object for configuring exception handling
         */
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        //http.httpBasic(withDefaults());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    /**
     * Configures web security to ignore specific request patterns
     *
     * This method sets up the security configuration to allow access to certain endpoints
     * without authentication. It is typically used for public resources like API documentation,
     * Swagger UI, and static files.
     *
     * @return A WebSecurityCustomizer configured to ignore specified request patterns
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web ->  web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "configuration/security",
                "/swagger-ui.html",
                "/webjars/**"));
    }
}
