package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    Optional<AddressEntity> findByAddress(String address);
}
