package com.mohan.spring_batch_revision.config;

import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.entity.Transaction;
import com.mohan.spring_batch_revision.listeners.CustomItemWriteListener;
import com.mohan.spring_batch_revision.processor.CustomerProcessor;
import com.mohan.spring_batch_revision.processor.TransactionProcessor;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import com.mohan.spring_batch_revision.writer.CustomerRepositoryWriter;
import com.mohan.spring_batch_revision.writer.TransactionRepositoryWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomerRepositoryWriter customerRepositoryWriter;
    private final CustomItemWriteListener customItemWriteListener;
    private final FlatFileItemReader<Customer> customerLoadItemReader;
    private final FlatFileItemReader<Transaction> transactionLoadItemReader;
    private final TransactionRepositoryWriter transactionRepositoryWriter;
    private final TransactionProcessor transactionProcessor;

    public SpringBatchConfig(CustomerRepository customerRepository, JobRepository jobRepository,
                             PlatformTransactionManager transactionManager, CustomerRepositoryWriter customerRepositoryWriter,
                             CustomItemWriteListener customItemWriteListener,
                             @Qualifier("customerItemReader") @Lazy FlatFileItemReader<Customer> customerLoadItemReader,
                             @Qualifier("transactionItemReader") @Lazy FlatFileItemReader<Transaction> transactionLoadItemReader,
                             TransactionRepositoryWriter transactionRepositoryWriter, TransactionProcessor transactionProcessor) {
        this.customerRepository = customerRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.customerRepositoryWriter = customerRepositoryWriter;
        this.customItemWriteListener = customItemWriteListener;
        this.customerLoadItemReader = customerLoadItemReader;
        this.transactionLoadItemReader = transactionLoadItemReader;
        this.transactionRepositoryWriter = transactionRepositoryWriter;
        this.transactionProcessor = transactionProcessor;
    }

    //    private LineMapper<Customer> lineMapper() {
//        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
//
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setNames("firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
//        lineMapper.setLineTokenizer(lineTokenizer);
//
//        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = getCustomerBeanWrapperFieldSetMapper();
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return lineMapper;
//    }
//
//    private static BeanWrapperFieldSetMapper<Customer> getCustomerBeanWrapperFieldSetMapper() {
//        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>() {
//            @Override
//            protected void initBinder(DataBinder binder) {
//                super.initBinder(binder);
//                binder.registerCustomEditor(LocalDate.class,
//                        new PropertyEditorSupport() {
//                            @Override
//                            public void setAsText(String text) {
//                                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//                            }
//                        });
//            }
//        };
//        fieldSetMapper.setTargetType(Customer.class);
//        return fieldSetMapper;
//    }

    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }

//    @Bean
//    public ItemWriter<Customer> writer(){
//        RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
//        itemWriter.setRepository(customerRepository);
//        itemWriter.setMethodName("save");
//        return itemWriter;
//    }

    @Bean
    public Step loadCustomersStep() {
        return new StepBuilder("load-customer-csv-step", jobRepository)
                .<Customer, Customer>chunk(100, transactionManager)
                .reader(customerLoadItemReader)
                .processor(customerProcessor())
                .writer(customerRepositoryWriter)
                .listener(customItemWriteListener)
                .build();

    }

    @Bean
    public Step loadTransactionStep(){
        return new StepBuilder("load-transaction-csv-step", jobRepository)
                .<Transaction, Transaction>chunk(500, transactionManager)
                .reader(transactionLoadItemReader)
                .processor(transactionProcessor)
                .writer(transactionRepositoryWriter)
                .build();
    }

    @Bean
    public Job loadJob() {
        return new JobBuilder("load-job", jobRepository)
                .flow(loadCustomersStep())
                .next(loadTransactionStep())
                .end().build();
    }


}
