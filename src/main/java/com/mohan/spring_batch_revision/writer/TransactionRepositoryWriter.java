package com.mohan.spring_batch_revision.writer;

import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionRepositoryWriter implements ItemWriter<Transaction> {

    private final TransactionRepository transactionRepository;

    public TransactionRepositoryWriter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void write(Chunk<? extends Transaction> chunk) throws Exception {
        log.info("Starting transaction processing");
        transactionRepository.saveAll(chunk);
    }
}
