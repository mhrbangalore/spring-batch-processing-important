package com.mohan.spring_batch_revision.processor;

import com.mohan.spring_batch_revision.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    @Override
    public Transaction process(Transaction item) throws Exception {
        item.setInsertTime(LocalDateTime.now());
        return item;
    }
}
