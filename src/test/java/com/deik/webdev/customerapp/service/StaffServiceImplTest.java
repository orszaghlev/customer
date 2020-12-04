package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StaffDao;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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
    public void testRecordStaff() throws UnknownStoreException, UnknownCountryException {
        Staff staff = getStaff();
        service.recordStaff(staff);

        verify(dao, times(1)).createStaff(staff);
    }

    @Test
    void testRecordStaffWithUnknownCountry() throws UnknownStoreException, UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createStaff(any());

        assertThrows(UnknownCountryException.class, ()->{
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
    void testDeleteStaff() throws UnknownStaffException {
        Staff staff = getStaff();
        service.deleteStaff(staff);

        verify(dao, times(1)).deleteStaff(staff);
    }

    @Test
    void testUpdateStaff() throws UnknownStoreException, UnknownCountryException, UnknownStaffException {
        Staff staff = getStaff();
        Staff newStaff = getNewStaff();
        service.updateStaff(staff, newStaff);

        verify(dao, times(1)).updateStaff(staff, newStaff);
    }

    private Staff getStaff() {
        return new Staff(
                "1",
                "firstName",
                "lastName",
                "address",
                "city",
                "country",
                "email",
                "store",
                "1",
                "username",
                "password"
        );
    }

    private Staff getNewStaff() {
        return new Staff(
                "1",
                "newFirstName",
                "newLastName",
                "newAddress",
                "newCity",
                "newCountry",
                "newEmail",
                "newStore",
                "2",
                "newUsername",
                "newPassword"
        );
    }

    private Collection<Staff> getDefaultStaffs() {
        return Arrays.asList(
                new Staff(
                        "1",
                        "firstName",
                        "lastName",
                        "address",
                        "city",
                        "country",
                        "email",
                        "store",
                        "1",
                        "username",
                        "password"
                ),
                new Staff(
                        "2",
                        "firstName1",
                        "lastName1",
                        "address1",
                        "city1",
                        "country1",
                        "email1",
                        "store1",
                        "2",
                        "username1",
                        "password1"
                ),
                new Staff(
                        "3",
                        "firstName2",
                        "lastName2",
                        "address2",
                        "city2",
                        "country2",
                        "email2",
                        "store2",
                        "3",
                        "username2",
                        "password2"
                ));
    }

}
