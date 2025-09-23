package com.mohan.spring_batch_revision.repository;

import com.mohan.spring_batch_revision.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerNo(Long customerNo);

}
