package com.mohan.spring_batch_revision.processor;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.exception.CustomerNotPresent;
import com.mohan.spring_batch_revision.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

    private final CustomerService customerService;

    public TransactionProcessor(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Transaction process(Transaction item) throws Exception {
        item.setInsertTime(LocalDateTime.now());
        Long customerNo = item.getCustomerNo();
        Customer customer = customerService.getCustomerByCardNo(customerNo);
        if (customer == null){
            log.info("Card Number is not present. Transaction processing skipped for {}", customerNo);
            return null;
        }
        item.setCustomerId(customer.getCustomerId());
        return item;
    }
}
