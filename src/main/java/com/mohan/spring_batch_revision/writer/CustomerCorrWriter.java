package com.mohan.spring_batch_revision.writer;

import com.mohan.spring_batch_revision.dto.CustomerEvent;
import com.mohan.spring_batch_revision.entity.Customer;
import com.mohan.spring_batch_revision.repository.CustomerRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class CustomerCorrWriter implements ItemWriter<CustomerEvent> {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;
    private final CustomerRepository customerRepository;

    public CustomerCorrWriter(KafkaTemplate<String, Object> kafkaTemplate,
                              @Value("${kafka.topic}")String topic,
                              CustomerRepository customerRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.customerRepository = customerRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerEvent> items) throws Exception {
        List<Future<SendResult<String, Object>>> futures = new ArrayList<>();

        for (CustomerEvent ev : items) {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, ev.getCustomerId().toString(), ev);
            Future<SendResult<String, Object>> f = kafkaTemplate.send(record);
            futures.add(f);
        }

        for (Future<SendResult<String, Object>> f : futures) {
            try {
                f.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                // allow Batch's retry/skip to handle it
                throw new KafkaException("Failed to send message to Kafka", e);
            }
        }

        for (CustomerEvent ev : items) {
            // load entity, set indicator to 'Y' and save
            customerRepository.findById(ev.getCustomerId()).ifPresent(c -> {
                c.setCorrInd('Y');
                customerRepository.save(c);
            });
        }
    }























}
