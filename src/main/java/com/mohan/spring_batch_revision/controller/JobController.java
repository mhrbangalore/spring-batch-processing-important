package com.mohan.spring_batch_revision.controller;

import com.mohan.spring_batch_revision.entity.JobStatusResponse;
import com.mohan.spring_batch_revision.loggers.BatchLogFileSetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping("/run-customer-load")
    public ResponseEntity<JobStatusResponse> runLoadJob(){
        BatchLogFileSetter.setBatchLogFile();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        String status;
        String message;
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            status = jobExecution.getStatus().toString();
            message = "COMPLETED".equals(status) ? "Job completed successfully" : "Job failed. Check logs for more information";
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error("Exception encountered during run load job execution: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        JobStatusResponse response = JobStatusResponse.builder()
                .status(status)
                .message(message)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


















}
