package com.mohan.spring_batch_revision.reader;

import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.utils.FileNamesSupplier;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class LoadTransactionsItemReaderConfig {

    private final FileNamesSupplier fileNamesSupplier;

    public LoadTransactionsItemReaderConfig(FileNamesSupplier fileNamesSupplier) {
        this.fileNamesSupplier = fileNamesSupplier;
    }

    @Bean(name = "transactionItemReader")
    public FlatFileItemReader<Transaction> transactionLoadIteReader(){
        FlatFileItemReader<Transaction> itemReader = new FlatFileItemReader<>();
        itemReader.setName("csv-transaction-reader");
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new ClassPathResource(fileNamesSupplier.getTransactionLoadFileName()));
        itemReader.setLineMapper(transactionLineMapper());
        return itemReader;
    }

    private LineMapper<Transaction> transactionLineMapper() {
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setStrict(false);
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("customerNo", "uniqueTranId", "transactionDate", "amount", "description");
        lineMapper.setLineTokenizer(lineTokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            Transaction transaction = Transaction.builder()
                    .customerNo(fieldSet.readLong("customerNo"))
                    .uniqueTranId(fieldSet.readString("uniqueTranId"))
                    .description(fieldSet.readString("description"))
                    .amount(new BigDecimal(fieldSet.readString("amount")))
                    .build();

            String transactionDateStr = fieldSet.readString("transactionDate");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            transaction.setTransactionDate(LocalDate.parse(transactionDateStr, formatter));

            return transaction;
        });
        return lineMapper;
    }
}
