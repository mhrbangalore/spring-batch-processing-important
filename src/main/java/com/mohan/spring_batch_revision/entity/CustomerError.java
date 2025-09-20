package com.mohan.spring_batch_revision.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_error")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerError {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String gender;

    @Column(name = "contact_no")
    private String contactNo;

    private String country;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;
}
