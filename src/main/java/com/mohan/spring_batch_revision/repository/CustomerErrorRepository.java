package com.mohan.spring_batch_revision.repository;

import com.mohan.spring_batch_revision.entity.CustomerError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerErrorRepository extends JpaRepository<CustomerError, Long> {
}
