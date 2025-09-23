package com.mohan.spring_batch_revision.reader;

import com.mohan.spring_batch_revision.entity.Customer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CustomerCorrJdbcReader {

    private final DataSource dataSource;

    public CustomerCorrJdbcReader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcCursorItemReader<Customer> reader(){
        JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM customers WHERE corr_ind = 'N'");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Customer.class));
        return reader;
    }
}
