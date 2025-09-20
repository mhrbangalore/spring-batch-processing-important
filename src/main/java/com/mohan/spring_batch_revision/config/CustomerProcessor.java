package com.mohan.spring_batch_revision.config;

import com.mohan.spring_batch_revision.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        item.setCorrInd('N');
        item.setCreatedAt(LocalDateTime.now());
        return item;
    }
}
