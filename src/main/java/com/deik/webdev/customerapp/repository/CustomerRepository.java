package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
