package com.mohan.spring_batch_revision.config;

import com.mohan.spring_batch_revision.loggers.JobLoggerListener;
import com.mohan.spring_batch_revision.step.LoadConsumerStep;
import com.mohan.spring_batch_revision.step.LoadTransactionStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final JobLoggerListener jobLoggerListener;
    private final LoadConsumerStep loadConsumerStep;
    private final LoadTransactionStep loadTransactionStep;

    public SpringBatchConfig(JobRepository jobRepository,
                             JobLoggerListener jobLoggerListener,
                             LoadConsumerStep loadConsumerStep, LoadTransactionStep loadTransactionStep) {
        this.jobRepository = jobRepository;
        this.jobLoggerListener = jobLoggerListener;
        this.loadConsumerStep = loadConsumerStep;
        this.loadTransactionStep = loadTransactionStep;
    }

    @Bean
    public Job loadJob() {
        return new JobBuilder("load-job", jobRepository)
                .listener(jobLoggerListener)
                .flow(loadConsumerStep.loadCustomersStep())
                .next(loadTransactionStep.loadTransactionStep())
                .end().build();
    }


}
