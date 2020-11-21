package com.deik.webdev.customerapp.repository;

import com.deik.webdev.customerapp.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {

}
