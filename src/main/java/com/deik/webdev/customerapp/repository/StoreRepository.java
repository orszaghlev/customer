package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {
    Optional<StoreEntity> findById(int id);
    Collection<StoreEntity> findByStaffId(Integer staffId);
}
