package com.mohan.spring_batch_revision.step;

import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.processor.TransactionProcessor;
import com.mohan.spring_batch_revision.reader.LoadTransactionsItemReaderConfig;
import com.mohan.spring_batch_revision.writer.TransactionRepositoryWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class LoadTransactionStep {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FlatFileItemReader<Transaction> transactionLoadItemReader;
    private final TransactionRepositoryWriter transactionRepositoryWriter;
    private final TransactionProcessor transactionProcessor;

    public LoadTransactionStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               @Qualifier("transactionItemReader") @Lazy FlatFileItemReader<Transaction> transactionLoadItemReader,
                               TransactionRepositoryWriter transactionRepositoryWriter,
                               TransactionProcessor transactionProcessor) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.transactionLoadItemReader = transactionLoadItemReader;
        this.transactionRepositoryWriter = transactionRepositoryWriter;
        this.transactionProcessor = transactionProcessor;
    }

    public Step loadTransactionStep(){
        return new StepBuilder("load-transaction-csv-step", jobRepository)
                .<Transaction, Transaction>chunk(500, transactionManager)
                .reader(transactionLoadItemReader)
                .processor(transactionProcessor)
                .writer(transactionRepositoryWriter)
                .build();
    }
}
