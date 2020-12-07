package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    Optional<CityEntity> findByCity(String city);
    Collection<CityEntity> findByCountry(Optional<CountryEntity> country);
}
