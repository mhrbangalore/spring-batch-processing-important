package com.mohan.spring_batch_revision.readers;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.service.FileInformationService;
import com.mohan.spring_batch_revision.utils.FileNamesSupplier;
import com.mohan.spring_batch_revision.utils.FileTypes;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class LoadCustomersItemReaderConfig {

    private final FileNamesSupplier fileNamesSupplier;

    public LoadCustomersItemReaderConfig(FileNamesSupplier fileNamesSupplier) {
        this.fileNamesSupplier = fileNamesSupplier;
    }

    @Bean(name = "customerItemReader")
    @Lazy
    public FlatFileItemReader<Customer> customerLoadItemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setName("csv-customer-reader");
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new ClassPathResource(fileNamesSupplier.getCustomerLoadFileName()));
        itemReader.setLineMapper(customerLineMapper());
        return itemReader;
    }

    private LineMapper<Customer> customerLineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setStrict(false);
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
        lineMapper.setLineTokenizer(lineTokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            Customer customer = new Customer();
            customer.setFirstName(fieldSet.readString("firstName").trim());
            customer.setLastName(fieldSet.readString("lastName").trim());
            customer.setEmail(fieldSet.readString("email").trim());
            customer.setGender(fieldSet.readString("gender").trim());
            customer.setContactNo(fieldSet.readString("contactNo").trim());
            customer.setCountry(fieldSet.readString("country").trim());

            String dobStr = fieldSet.readString("dob");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            customer.setDob(LocalDate.parse(dobStr, formatter));

            return customer;
        });

        return lineMapper;
    }

}
