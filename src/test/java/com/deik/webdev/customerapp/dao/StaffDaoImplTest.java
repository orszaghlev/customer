package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StoreEntity;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffDaoImplTest {

    @Spy
    @InjectMocks
    private StaffDaoImpl dao;
    @Mock
    private StaffRepository staffRepository;

    @Test
    void testCreateStaff() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().id(1).build())
                .when(dao).queryAddress(anyInt());
        doReturn(StoreEntity.builder().id(1).build())
                .when(dao).queryStore(anyInt());
        dao.createStaff(getStaff());

        verify(staffRepository, times(1)).save(any());
    }

    @Test
    void testReadAllStaff() {
        Collection<Staff> staffCollection = dao.readAll();

        assertThat(Collections.emptyList(), is(staffCollection));
    }

    @Test
    public void deleteStaff() throws UnknownStaffException {
        doThrow(UnknownStaffException.class).when(dao).deleteStaff(any());

        assertThrows(UnknownStaffException.class, ()->{
            dao.deleteStaff(getStaff());
        });
    }

    @Test
    public void updateStaff() throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException {
        doThrow(UnknownStaffException.class).when(dao).updateStaff(any(), any());

        assertThrows(UnknownStaffException.class, ()->{
            dao.updateStaff(getStaff(), getNewStaff());
        });
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

}
