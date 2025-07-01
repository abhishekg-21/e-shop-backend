// src/main/java/com/eshop/e_shop_backend/model/Role.java
package com.eshop.e_shop_backend.model;

import jakarta.persistence.*;
import lombok.Data; // <<<--- IMPORTANT: MAKE SURE THIS IS IMPORTED
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator; // IMPORTANT: Import for UUID generation

@Entity
@Table(name = "roles") // Ensure your table name is correct
@Data // <<<--- CRITICAL: THIS ANNOTATION GENERATES GETTERS/SETTERS FOR ALL FIELDS
@NoArgsConstructor // Lombok: Generates no-argument constructor
public class Role {

    @Id
    @GeneratedValue(generator = "uuid2") // <<<--- THIS IS THE UUID GENERATION STRATEGY
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator") // <<<--- THIS DEFINES THE UUID
                                                                                   // GENERATOR
    @Column(name = "id", columnDefinition = "VARCHAR(255)") // <<<--- THIS SETS THE DB COLUMN TYPE TO VARCHAR
    private String id; // <<<--- The Java type for UUIDs MUST BE String

    @Column(nullable = false, unique = true)
    private String name; // e.g., "ROLE_ADMIN", "ROLE_USER"

    // No other fields for now, keep it simple for roles
}
