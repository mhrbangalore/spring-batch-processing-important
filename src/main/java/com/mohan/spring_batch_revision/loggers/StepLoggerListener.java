package com.mohan.spring_batch_revision.loggers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepLoggerListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(StepLoggerListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step Started: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step Ended: {}. Read: {}, Written: {}, Skipped: {}, Failures: {}",
                stepExecution.getStepName(),
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getSkipCount(),
                stepExecution.getFailureExceptions().size());
        return stepExecution.getExitStatus();
    }

}
