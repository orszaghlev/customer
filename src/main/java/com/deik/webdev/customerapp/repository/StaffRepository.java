package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
    Collection<StaffEntity> findByUsername(String username);
    Collection<StaffEntity> findByEmail(String email);
}
