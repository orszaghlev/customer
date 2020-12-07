package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    Optional<AddressEntity> findByAddress(String address);
    Collection<AddressEntity> findByCity(Optional<CityEntity> city);
    Collection<AddressEntity> findByDistrict(String district);
    Collection<AddressEntity> findByPostalCode(String postalCode);
}
