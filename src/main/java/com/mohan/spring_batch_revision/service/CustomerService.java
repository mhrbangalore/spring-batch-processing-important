package com.mohan.spring_batch_revision.service;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.exception.CustomerNotPresent;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByCardNo(Long customerNo){
        return customerRepository.findByCustomerNo(customerNo)
                .orElse(null);
    }
}
