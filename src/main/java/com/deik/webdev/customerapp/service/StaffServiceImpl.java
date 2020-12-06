package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StaffDao;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffDao staffDao;

    @Override
    public Collection<Staff> getAllStaff() {
        return staffDao.readAll();
    }

    @Override
    public Collection<Staff> getStaffByUsername(String username) throws UnknownStaffException {
        return staffDao.readStaffByUsername(username);
    }

    @Override
    public Collection<Staff> getStaffByEmail(String email) throws UnknownStaffException {
        return staffDao.readStaffByEmail(email);
    }

    @Override
    public void recordStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        staffDao.createStaff(staff);
    }

    @Override
    public void deleteStaff(Staff staff) throws UnknownStaffException {
        staffDao.deleteStaff(staff);
    }

    @Override
    public void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException {
        staffDao.updateStaff(staff, newStaff);
    }

}
