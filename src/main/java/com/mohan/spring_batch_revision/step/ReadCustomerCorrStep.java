package com.mohan.spring_batch_revision.step;

import com.mohan.spring_batch_revision.dto.CustomerEvent;
import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.loggers.ChunkLoggerListener;
import com.mohan.spring_batch_revision.loggers.StepLoggerListener;
import com.mohan.spring_batch_revision.processor.CustomerCorrProcessor;
import com.mohan.spring_batch_revision.reader.CustomerCorrJdbcReader;
import com.mohan.spring_batch_revision.reader.CustomerJpaPagingItemReader;
import com.mohan.spring_batch_revision.writer.CustomerCorrWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class ReadCustomerCorrStep {

//    private final CustomerJpaPagingItemReader reader;
    private final CustomerCorrJdbcReader reader;
    private final CustomerCorrProcessor processor;
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final CustomerCorrWriter writer;
    private final StepLoggerListener stepLoggerListener;
    private final ChunkLoggerListener chunkLoggerListener;

    public ReadCustomerCorrStep(CustomerCorrJdbcReader reader, CustomerCorrProcessor processor,
                                PlatformTransactionManager transactionManager,
                                JobRepository jobRepository, CustomerCorrWriter writer, StepLoggerListener stepLoggerListener,
                                ChunkLoggerListener chunkLoggerListener) {
        this.reader = reader;
        this.processor = processor;
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.writer = writer;
        this.stepLoggerListener = stepLoggerListener;
        this.chunkLoggerListener = chunkLoggerListener;
    }

    public Step readCustomerCorrIndStep(){
        return new StepBuilder("read-customer-corr-ind", jobRepository)
                .<Customer, CustomerEvent>chunk(100, transactionManager)
                .reader(reader.reader())
                .processor(processor)
                .writer(writer)
                .listener(stepLoggerListener)
                .listener(chunkLoggerListener)
                .build();
    }
}
