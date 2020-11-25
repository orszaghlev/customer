package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> getAllStaff();

    void recordStaff(Staff staff) throws UnknownCountryException;
    void deleteStaff(Staff staff) throws UnknownStaffException;

}
