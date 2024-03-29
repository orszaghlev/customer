package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
    Optional<StaffEntity> findByUsername(String username);
    Collection<StaffEntity> findByFirstNameAndLastName(String firstName, String lastName);
    Collection<StaffEntity> findByEmail(String email);
    Collection<StaffEntity> findByStoreId(Integer storeId);
    Collection<StaffEntity> findByActive(Integer active);
}
