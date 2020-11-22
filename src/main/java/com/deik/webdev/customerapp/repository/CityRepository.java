package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    Collection<CityEntity> findByName(String name);
}
