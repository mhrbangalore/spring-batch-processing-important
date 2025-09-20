package com.mohan.spring_batch_revision.writer;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerRepositoryWriter implements ItemWriter<Customer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void write(Chunk<? extends Customer> chunk) {
        log.info("Saving records in chunk size: {}", chunk.size());
        try {
            customerRepository.saveAll(chunk);
            entityManager.flush();
        } catch (ConstraintViolationException ex) {
            log.error("Constraint violation while inserting records: {}", ex.getMessage());
            throw ex;  // rethrow so Spring Batch triggers onWriteError
        } catch (Exception ex) {
            log.error("Unexpected exception during write: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
