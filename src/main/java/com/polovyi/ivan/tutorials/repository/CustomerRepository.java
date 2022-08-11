package com.polovyi.ivan.tutorials.repository;

import com.polovyi.ivan.tutorials.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    List<CustomerEntity> findAllByCreatedAtBetween(LocalDate startDateTime, LocalDate endDateTime);
}
