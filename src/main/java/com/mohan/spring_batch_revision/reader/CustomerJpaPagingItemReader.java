package com.mohan.spring_batch_revision.reader;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaPagingItemReader {

    private final CustomerRepository customerRepository;
    private final EntityManagerFactory entityManagerFactory;

    public CustomerJpaPagingItemReader(CustomerRepository customerRepository, EntityManagerFactory entityManagerFactory) {
        this.customerRepository = customerRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    public JpaPagingItemReader<Customer> customersCorrReader(){
        JpaPagingItemReader<Customer> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(this.entityManagerFactory);
        reader.setQueryString("SELECT c from Customer c WHERE c.corrInd = 'N'");
        reader.setPageSize(100);
        reader.setSaveState(true);
        return reader;
    }
}
