package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findById(int id);
    Collection<CustomerEntity> findByFirstNameAndLastName(String firstName, String lastName);
    Collection<CustomerEntity> findByEmail(String email);
}
