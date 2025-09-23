package com.mohan.spring_batch_revision.loggers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobLoggerListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobLoggerListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Started: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job Ended: {} with status {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());
    }

}
