package com.mohan.spring_batch_revision.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_no", unique = true, nullable = false)
    @NotNull
    private Long customerNo;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String gender;

    @Column(name = "contact_no", nullable = false)
    @NotBlank
    private String contactNo;

    @Column(nullable = false)
    @NotBlank
    private String country;

    @Column(name = "birth_date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @Column(name = "corr_ind", nullable = false)
    private Character corrInd;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
