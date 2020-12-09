package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StoreEntity;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testCreateStaff() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().address("address").build())
                .when(dao).queryAddress(any());
        doReturn(StoreEntity.builder().id(1).build())
                .when(dao).queryStore(anyInt());
        dao.createStaff(getStaff());

        verify(staffRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllStaff() {
        dao.readAll();

        verify(staffRepository, times(1)).findAll();
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

}
