package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffDao {

    void createStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    Collection<Staff> readAll();
    Collection<Staff> readStaffByUsername(String username) throws UnknownStaffException;
    Collection<Staff> readStaffByEmail(String email) throws UnknownStaffException;
    Collection<Staff> readStaffByStoreId(Integer storeId) throws UnknownStaffException, OutOfBoundsException;

    void deleteStaff(Staff staff) throws UnknownStaffException;

    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException;

}
