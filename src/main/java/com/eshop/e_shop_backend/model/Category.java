// src/main/java/com/eshop/e_shop_backend/model/Category.java
package com.eshop.e_shop_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode; // Import this

import org.hibernate.annotations.GenericGenerator;

import java.util.Set; // If you have a Set<Product> here

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // Add this
public class Category {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    // If you have a bidirectional relationship with Product (e.g., a list of
    // products in this category)
    // @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // @EqualsAndHashCode.Exclude // <--- EXCLUDE PRODUCTS FROM EQUALS/HASHCODE
    // private Set<Product> products;
}
