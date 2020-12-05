package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> getAllStaff();

    void recordStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    void deleteStaff(Staff staff) throws UnknownStaffException;
    void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException;

}
