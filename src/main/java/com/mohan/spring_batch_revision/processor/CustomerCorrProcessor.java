package com.mohan.spring_batch_revision.processor;

import com.mohan.spring_batch_revision.dto.CustomerEvent;
import com.mohan.spring_batch_revision.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerCorrProcessor implements ItemProcessor<Customer, CustomerEvent>{

    @Override
    public CustomerEvent process(Customer item) throws Exception {
        return CustomerEvent.builder()
                .customerId(item.getCustomerId())
                .customerNo(item.getCustomerNo())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .dob(item.getDob())
                .build();
    }
}
