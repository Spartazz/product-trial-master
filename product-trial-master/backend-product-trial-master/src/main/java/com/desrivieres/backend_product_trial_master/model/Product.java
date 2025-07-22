package com.desrivieres.backend_product_trial_master.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", schema = "product_trial__master_db")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version  // Ajouter cette annotation pour la gestion de version
    private Long version;

    @NotBlank(message = "Code cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "Only alphanumeric characters are allowed"
    )
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "category")
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 50)
    private String category;

    @NotBlank(message = "Price cannot be blank")
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Min(0)
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NotBlank(message = "Internal Reference cannot be blank")
    @Column(name = "internal_reference", nullable = false, unique = true)
    private String internalReference;

    @Min(0)
    @Column(name = "shell_id", nullable = false)
    private int shellId;

    @NotBlank(message = "Inventory Status cannot be blank")
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @Min(0)
    @Max(5)
    @Column(name = "rating", nullable = false)
    private int rating;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

}

