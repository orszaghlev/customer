package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> getAllStaff();

    void recordStaff(Staff staff) throws UnknownStoreException;
    void deleteStaff(Staff staff) throws UnknownStaffException;

}
