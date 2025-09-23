package com.mohan.spring_batch_revision.listeners;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.service.CustomerErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerSkipListener implements SkipListener<Customer, Customer> {

    @Autowired
    private CustomerErrorService customerErrorService;

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Skipping Customer due to error during read: {}", t.getMessage());
    }

    @Override
    public void onSkipInProcess(Customer customer, Throwable t) {
        System.err.println("Skipped record during process: " + customer + " due to " + t.getMessage());
        customerErrorService.saveError(customer, t.getMessage());
    }

    @Override
    public void onSkipInWrite(Customer customer, Throwable t) {
        log.error("Skipping Customer due to error: {}", t.getMessage());
        customerErrorService.saveError(customer, t.getMessage());
    }
}
