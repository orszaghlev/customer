package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

}
