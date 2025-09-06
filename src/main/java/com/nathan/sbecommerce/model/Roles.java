package com.nathan.sbecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    @Column(length = 20, name = "role_name")
    private AppRole appRole;

    public Roles(AppRole appRole) {
        this.appRole = appRole;
    }
}
