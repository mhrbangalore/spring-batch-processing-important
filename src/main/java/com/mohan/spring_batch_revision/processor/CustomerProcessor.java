package com.mohan.spring_batch_revision.processor;

import com.mohan.spring_batch_revision.entity.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Autowired
    private Validator validator;

    @Override
    public Customer process(Customer item) throws Exception {

        Set<ConstraintViolation<Customer>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            throw new ValidationException("Invalid record: " + violations);
        }

        item.setCorrInd('N');
        item.setCreatedAt(LocalDateTime.now());
        return item;
    }
}
