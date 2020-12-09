package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StaffDao;
import com.deik.webdev.customerapp.exception.*;
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
    public Collection<Staff> getStaffByFirstNameAndLastName(String firstName, String lastName) throws UnknownStaffException, EmptyException {
        return staffDao.readStaffByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Collection<Staff> getStaffByEmail(String email) throws UnknownStaffException, EmptyException {
        return staffDao.readStaffByEmail(email);
    }

    @Override
    public Collection<Staff> getStaffByStoreId(Integer storeId) throws UnknownStaffException, EmptyException, OutOfBoundsException {
        return staffDao.readStaffByStoreId(storeId);
    }

    @Override
    public Collection<Staff> getStaffByActivity(Integer active) throws UnknownStaffException, OutOfBoundsException, EmptyException {
        return staffDao.readStaffByActivity(active);
    }

    @Override
    public Staff getStaffById(Integer id) throws UnknownStaffException, OutOfBoundsException, EmptyException {
        return staffDao.readStaffById(id);
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
