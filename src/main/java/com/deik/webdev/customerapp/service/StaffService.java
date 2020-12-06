package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> getAllStaff();
    Collection<Staff> getStaffByUsername(String username) throws UnknownStaffException;
    Collection<Staff> getStaffByEmail(String email) throws UnknownStaffException;
    Collection<Staff> getStaffByStoreId(Integer storeId) throws UnknownStaffException, OutOfBoundsException;

    void recordStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    void deleteStaff(Staff staff) throws UnknownStaffException;
    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException;

}
