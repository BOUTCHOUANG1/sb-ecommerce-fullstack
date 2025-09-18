package com.nathan.sbecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "Product Name must contain atleast 3 characters")
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_image")
    private String image;

    @NotBlank
    @Size(min = 20, message = "Product Description must contain atleast 20 characters")
    private String description;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_discount")
    private Double discount;

    @Column(name = "product_special_price")
    private Double specialPrice;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users user;
}
