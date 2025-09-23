package com.mohan.spring_batch_revision.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "customer_no")
    private Long customerNo;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "unique_tran_id", unique = true)
    private String uniqueTranId;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate transactionDate;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    private BigDecimal amount;

    private String description;
}
