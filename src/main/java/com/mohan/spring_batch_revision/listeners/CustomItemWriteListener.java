package com.mohan.spring_batch_revision.listeners;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.entity.CustomerError;
import com.mohan.spring_batch_revision.repository.CustomerErrorRepository;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import com.mohan.spring_batch_revision.service.CustomerErrorService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CustomItemWriteListener implements ItemWriteListener<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerErrorRepository customerErrorRepository;

    @Autowired
    private CustomerErrorService customerErrorService;

    @Override
    public void beforeWrite(Chunk<? extends Customer> items) {
        log.info("About to write {} records", items.size());
    }

    @Override
    public void afterWrite(Chunk<? extends Customer> items) {
        log.info("Successfully wrote {} records to customers table", items.size());
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Customer> items) {
        log.error("Error experienced during insertion of customers. Moving the failed records to customer_error table. Please check the table.");
        for (Customer customer : items){
            try {
                customerErrorService.saveError(customer, exception.getMessage());
                log.warn("❌ Error record persisted: {} | {}", customer.getFirstName(), customer.getEmail());
            } catch (ConstraintViolationException ex) {
                log.error("Constraint violation while inserting records: {}", ex.getMessage());
            } catch (Exception ex){
                log.error("Failed to persist error record for {}: {}", customer.getEmail(), ex.getMessage());
            }
        }
    }



























}
