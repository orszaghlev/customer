package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {

}
