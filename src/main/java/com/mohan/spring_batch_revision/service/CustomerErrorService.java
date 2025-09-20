package com.mohan.spring_batch_revision.service;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.entity.CustomerError;
import com.mohan.spring_batch_revision.repository.CustomerErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerErrorService {

    @Autowired
    private CustomerErrorRepository customerErrorRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Customer customer, String message) {

        String safeMessage = message != null && message.length() > 255
                ? message.substring(0, 500)
                : message;

        CustomerError error = CustomerError.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .contactNo(customer.getContactNo())
                .dob(customer.getDob())
                .email(customer.getEmail())
                .country(customer.getCountry())
                .createdAt(LocalDateTime.now())
                .gender(customer.getGender())
                .errorMessage(message)
                .build();
        customerErrorRepository.saveAndFlush(error);
    }
}
