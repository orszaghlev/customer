package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StaffDto;
import com.deik.webdev.customerapp.dto.StaffUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.service.StaffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffControllerTest {

    @InjectMocks
    private StaffController staffController;
    @Mock
    private StaffService staffService;

    @Test
    public void testListStaff() {
        when(staffService.getAllStaff()).thenReturn(getStaffs());
        staffController.listStaffs();

        verify(staffService, times(1)).getAllStaff();
    }

    @Test
    public void testListStaffByFirstNameAndLastName() throws UnknownStaffException, EmptyException {
        when(staffService.getStaffByFirstNameAndLastName(any(), any())).thenReturn(getStaffs());
        staffController.listStaffByFirstNameAndLastName(anyString(), anyString());

        verify(staffService, times(1)).getStaffByFirstNameAndLastName(anyString(), anyString());
    }

    @Test
    public void testListStaffByEmail() throws UnknownStaffException, EmptyException {
        when(staffService.getStaffByEmail(any())).thenReturn(getStaffs());
        staffController.listStaffByEmail(anyString());

        verify(staffService, times(1)).getStaffByEmail(anyString());
    }

    @Test
    public void testListStaffByStoreId() throws UnknownStaffException, EmptyException, OutOfBoundsException {
        when(staffService.getStaffByStoreId(any())).thenReturn(getStaffs());
        staffController.listStaffByStoreId(anyInt());

        verify(staffService, times(1)).getStaffByStoreId(anyInt());
    }

    @Test
    public void testListActiveStaff() throws UnknownStaffException, EmptyException, OutOfBoundsException {
        when(staffService.getActiveStaff(any())).thenReturn(getStaffs());
        staffController.listActiveStaff(anyInt());

        verify(staffService, times(1)).getActiveStaff(anyInt());
    }

    @Test
    public void testListStaffById() throws UnknownStaffException, EmptyException, OutOfBoundsException {
        when(staffService.getStaffById(any())).thenReturn(getStaff());
        staffController.listStaffById(anyInt());

        verify(staffService, times(1)).getStaffById(anyInt());
    }

    @Test
    public void testRecordStaff() throws UnknownAddressException, UnknownStoreException, OutOfBoundsException {
        staffController.recordStaff(getStaffDto());

        verify(staffService, times(1)).recordStaff(getStaff());
    }

    @Test
    public void testDeleteStaff() throws UnknownStaffException {
        staffController.deleteStaff(getStaffDto());

        verify(staffService, times(1)).deleteStaff(any());
    }

    @Test
    public void testUpdateStaff() throws UnknownStaffException, UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        staffController.updateStaff(getStaffUpdateRequestDto());

        verify(staffService, times(1)).updateStaff(any(), any());
    }

    private Staff getStaff() {
        return new Staff(
                1,
                "firstName",
                "lastName",
                "address",
                "email",
                1,
                0,
                "username",
                "password"
        );
    }

    private StaffDto getStaffDto() {
        return new StaffDto(
                1,
                "firstName",
                "lastName",
                "address",
                "email",
                1,
                0,
                "username",
                "password"
        );
    }

    private StaffUpdateRequestDto getStaffUpdateRequestDto() {
        return new StaffUpdateRequestDto(
                "firstName",
                "lastName",
                "address",
                "email",
                1,
                0,
                "username",
                "password"
        );
    }

    private Collection<Staff> getStaffs() {
        return Arrays.asList(
                new Staff(
                        1,
                        "firstName",
                        "lastName",
                        "address",
                        "email",
                        1,
                        0,
                        "username",
                        "password"
                ),
                new Staff(
                        2,
                        "firstName",
                        "lastName",
                        "address",
                        "email",
                        2,
                        1,
                        "username",
                        "password"
                ),
                new Staff(
                        3,
                        "firstName",
                        "lastName",
                        "address",
                        "email",
                        3,
                        0,
                        "username",
                        "password"
                ));
    }

}
