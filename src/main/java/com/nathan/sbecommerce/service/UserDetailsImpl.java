package com.nathan.sbecommerce.service;

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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Service
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends  GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

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
