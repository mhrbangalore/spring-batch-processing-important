package com.mohan.spring_batch_revision.step;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.listeners.CustomerSkipListener;
import com.mohan.spring_batch_revision.loggers.ChunkLoggerListener;
import com.mohan.spring_batch_revision.loggers.StepLoggerListener;
import com.mohan.spring_batch_revision.processor.CustomerProcessor;
import com.mohan.spring_batch_revision.reader.LoadCustomersItemReaderConfig;
import com.mohan.spring_batch_revision.writer.CustomerRepositoryWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class LoadConsumerStep {

    private final FlatFileItemReader<Customer> customerLoadItemReader;
    private final CustomerProcessor customerProcessor;
    private final CustomerRepositoryWriter customerRepositoryWriter;
    private final CustomerSkipListener customerSkipListener;
    private final StepLoggerListener stepLoggerListener;
    private final ChunkLoggerListener chunkLoggerListener;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    public LoadConsumerStep(@Qualifier("customerItemReader") @Lazy FlatFileItemReader<Customer> customerLoadItemReader,
                            CustomerProcessor customerProcessor,
                            CustomerRepositoryWriter customerRepositoryWriter,
                            CustomerSkipListener customerSkipListener,
                            StepLoggerListener stepLoggerListener,
                            ChunkLoggerListener chunkLoggerListener,
                            JobRepository jobRepository,
                            PlatformTransactionManager transactionManager
    ) {
        this.customerLoadItemReader = customerLoadItemReader;
        this.customerProcessor = customerProcessor;
        this.customerRepositoryWriter = customerRepositoryWriter;
        this.customerSkipListener = customerSkipListener;
        this.stepLoggerListener = stepLoggerListener;
        this.chunkLoggerListener = chunkLoggerListener;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    public Step loadCustomersStep() {
        return new StepBuilder("load-customer-csv-step", jobRepository)
                .<Customer, Customer>chunk(100, transactionManager)
                .reader(customerLoadItemReader)
                .processor(customerProcessor)
                .writer(customerRepositoryWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(3000)
                .listener(customerSkipListener)
                .listener(stepLoggerListener)
                .listener(chunkLoggerListener)
                .build();
    }
}
