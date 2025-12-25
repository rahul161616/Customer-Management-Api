package com.wowfs.customermanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // //Customer's full name (cannot be null or empty)
    // @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    // //Customer email has to be unique and valid
    // @Email(message = "Invalid email format")
    // @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true) //Ensures database-level uniqueness 
    private String email;

    // //Must be at least 18 years old
    // @Min(value = 18, message = "Age must be 18 or older")
    @Column(nullable = false)
    private Integer age;
    
    
    //Automatically set when record is created
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //This method runs just before entity is saved for the first time
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public String getEmail() {
        return this.email;
    }
 
}