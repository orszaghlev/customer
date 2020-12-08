package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffDao {

    void createStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    Collection<Staff> readAll();
    Collection<Staff> readStaffByUsername(String username) throws UnknownStaffException, EmptyException;
    Collection<Staff> readStaffByEmail(String email) throws UnknownStaffException, EmptyException;
    Collection<Staff> readStaffByStoreId(Integer storeId) throws UnknownStaffException, EmptyException;
    Collection<Staff> readActiveStaff(Integer active) throws UnknownStaffException, OutOfBoundsException, EmptyException;

    void deleteStaff(Staff staff) throws UnknownStaffException;

    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException;

}
