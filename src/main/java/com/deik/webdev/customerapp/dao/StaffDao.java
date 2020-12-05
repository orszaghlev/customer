package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffDao {

    void createStaff(Staff staff) throws UnknownStoreException, UnknownAddressException;
    Collection<Staff> readAll();

    void deleteStaff(Staff staff) throws UnknownStaffException;

    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException;

}
