package com.mohan.spring_batch_revision.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerEvent {

    private Long customerId;

    private Long customerNo;

    private String firstName;

    private String lastName;

    private LocalDate dob;

}
