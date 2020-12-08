package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> getAllStaff();
    Collection<Staff> getStaffByUsername(String username) throws UnknownStaffException, EmptyException;
    Collection<Staff> getStaffByEmail(String email) throws UnknownStaffException, EmptyException;
    Collection<Staff> getStaffByStoreId(Integer storeId) throws UnknownStaffException, EmptyException;
    Collection<Staff> getActiveStaff(Integer staff) throws UnknownStaffException, OutOfBoundsException, EmptyException;

    void recordStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    void deleteStaff(Staff staff) throws UnknownStaffException;
    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException;

}
