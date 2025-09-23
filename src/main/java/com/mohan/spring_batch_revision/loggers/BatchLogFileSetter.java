package com.mohan.spring_batch_revision.loggers;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BatchLogFileSetter {

    public static void setBatchLogFile() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = (Logger) LoggerFactory.getLogger("com.mohan.spring_batch_revision");

        FileAppender<?> fileAppender = (FileAppender<?>) logger.getAppender("BATCH_FILE");

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        fileAppender.stop(); // stop old file
        fileAppender.setFile("logs/batch-job-" + timestamp + ".log");
        fileAppender.start(); // start new file
    }

}
