package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StaffDao;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffServiceImplTest {

    @InjectMocks
    private StaffServiceImpl service;
    @Mock
    private StaffDao dao;

    @Test
    public void testRecordStaff() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        Staff staff = getStaff();
        service.recordStaff(staff);

        verify(dao, times(1)).createStaff(staff);
    }

    @Test
    void testRecordStaffWithUnknownAddress() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doThrow(UnknownAddressException.class).when(dao).createStaff(any());

        assertThrows(UnknownAddressException.class, ()->{
            service.recordStaff(getStaff());
        });
    }

    @Test
    void testReadAllStaffs() {
        when(dao.readAll()).thenReturn(getDefaultStaffs());
        Collection<Staff> actual = service.getAllStaff();

        assertThat(getDefaultStaffs(), is(actual));
    }

    @Test
    void testReadStaffFromUsername() throws UnknownStaffException {
        final String username = "username";
        Collection<Staff> actual = service.getStaffByUsername(username);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    void testReadStaffFromEmail() throws UnknownStaffException {
        final String email = "email";
        Collection<Staff> actual = service.getStaffByEmail(email);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    void testReadStaffFromStoreId() throws UnknownStaffException, OutOfBoundsException {
        final Integer storeId = 1;
        Collection<Staff> actual = service.getStaffByStoreId(storeId);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    void testReadActiveStaff() throws UnknownStaffException, OutOfBoundsException {
        final Integer active = 1;
        Collection<Staff> actual = service.getActiveStaff(active);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    void testDeleteStaff() throws UnknownStaffException {
        Staff staff = getStaff();
        service.deleteStaff(staff);

        verify(dao, times(1)).deleteStaff(staff);
    }

    @Test
    void testUpdateStaff() throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException {
        Staff staff = getStaff();
        Staff newStaff = getNewStaff();
        service.updateStaff(staff, newStaff);

        verify(dao, times(1)).updateStaff(staff, newStaff);
    }

    private Staff getStaff() {
        return new Staff(
                1,
                "firstName",
                "lastName",
                1,
                "email",
                1,
                0,
                "username",
                "password"
        );
    }

    private Staff getNewStaff() {
        return new Staff(
                2,
                "newFirstName",
                "newLastName",
                2,
                "newEmail",
                2,
                1,
                "newUsername",
                "newPassword"
        );
    }

    private Collection<Staff> getDefaultStaffs() {
        return Arrays.asList(
                new Staff(
                        1,
                        "firstName",
                        "lastName",
                        1,
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
                        2,
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
                        3,
                        "email",
                        3,
                        0,
                        "username",
                        "password"
                ));
    }

}
