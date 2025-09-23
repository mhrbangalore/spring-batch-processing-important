package com.mohan.spring_batch_revision.loggers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkLoggerListener implements ChunkListener {

    private static final Logger log = LoggerFactory.getLogger(ChunkLoggerListener.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("Chunk started in Step: {}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("Chunk completed. Items processed so far: {}",
                context.getStepContext().getStepExecution().getWriteCount());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Error occurred in Chunk of Step: {}",
                context.getStepContext().getStepName());
    }

}
