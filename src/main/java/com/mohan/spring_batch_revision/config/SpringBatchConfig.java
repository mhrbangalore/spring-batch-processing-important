package com.mohan.spring_batch_revision.config;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.listeners.CustomItemWriteListener;
import com.mohan.spring_batch_revision.listeners.CustomerSkipListener;
import com.mohan.spring_batch_revision.loggers.ChunkLoggerListener;
import com.mohan.spring_batch_revision.loggers.JobLoggerListener;
import com.mohan.spring_batch_revision.loggers.StepLoggerListener;
import com.mohan.spring_batch_revision.processor.CustomerProcessor;
import com.mohan.spring_batch_revision.processor.TransactionProcessor;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import com.mohan.spring_batch_revision.writer.CustomerRepositoryWriter;
import com.mohan.spring_batch_revision.writer.TransactionRepositoryWriter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomerRepositoryWriter customerRepositoryWriter;
    private final FlatFileItemReader<Customer> customerLoadItemReader;
    private final FlatFileItemReader<Transaction> transactionLoadItemReader;
    private final TransactionRepositoryWriter transactionRepositoryWriter;
    private final TransactionProcessor transactionProcessor;
    private final CustomerSkipListener customerSkipListener;
    private final JobLoggerListener jobLoggerListener;
    private final StepLoggerListener stepLoggerListener;
    private final ChunkLoggerListener chunkLoggerListener;

    public SpringBatchConfig(CustomerRepository customerRepository, JobRepository jobRepository,
                             PlatformTransactionManager transactionManager, CustomerRepositoryWriter customerRepositoryWriter,
                             @Qualifier("customerItemReader") @Lazy FlatFileItemReader<Customer> customerLoadItemReader,
                             @Qualifier("transactionItemReader") @Lazy FlatFileItemReader<Transaction> transactionLoadItemReader,
                             TransactionRepositoryWriter transactionRepositoryWriter, TransactionProcessor transactionProcessor, CustomerSkipListener customerSkipListener, JobLoggerListener jobLoggerListener, StepLoggerListener stepLoggerListener, ChunkLoggerListener chunkLoggerListener) {
        this.customerRepository = customerRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.customerRepositoryWriter = customerRepositoryWriter;
        this.customerLoadItemReader = customerLoadItemReader;
        this.transactionLoadItemReader = transactionLoadItemReader;
        this.transactionRepositoryWriter = transactionRepositoryWriter;
        this.transactionProcessor = transactionProcessor;
        this.customerSkipListener = customerSkipListener;
        this.jobLoggerListener = jobLoggerListener;
        this.stepLoggerListener = stepLoggerListener;
        this.chunkLoggerListener = chunkLoggerListener;
    }


    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }


    @Bean
    public Step loadCustomersStep() {
        return new StepBuilder("load-customer-csv-step", jobRepository)
                .<Customer, Customer>chunk(100, transactionManager)
                .reader(customerLoadItemReader)
                .processor(customerProcessor())
                .writer(customerRepositoryWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(3000)
                .listener(customerSkipListener)
                .listener(stepLoggerListener)
                .listener(chunkLoggerListener)
                .build();
    }

    @Bean
    public Step loadTransactionStep(){
        return new StepBuilder("load-transaction-csv-step", jobRepository)
                .<Transaction, Transaction>chunk(500, transactionManager)
                .reader(transactionLoadItemReader)
                .processor(transactionProcessor)
                .writer(transactionRepositoryWriter)
                .build();
    }

    @Bean
    public Job loadJob() {
        return new JobBuilder("load-job", jobRepository)
                .listener(jobLoggerListener)
                .flow(loadCustomersStep())
                .next(loadTransactionStep())
                .end().build();
    }


}
