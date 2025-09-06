package com.nathan.sbecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 2, max = 50, message = "Building name must contain atmost 50 and atleast 2 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 2, max = 50, message = "City must contain atmost 50 and atleast 2 characters")
    private String city;

    @NotBlank
    @Size(min = 2, max = 50, message = "Country must contain atmost 50 and atleast 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6, max = 6, message = "Pincode must contain 6 characters")
    private String pincode;

    @NotBlank
    @Size(min = 2, max = 50, message = "State must contain atmost 50 and atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min = 5, max = 100, message = "Street must contain atmost 100 and atleast 5 characters")
    private String street;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> users = new ArrayList<>();

    public Address(String country, String buildingName, String city, String pincode, String state, String street) {
        this.country = country;
        this.buildingName = buildingName;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.street = street;
    }
}
