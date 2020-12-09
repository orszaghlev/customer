package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StaffEntity;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Store;
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
public class StoreDaoImplTest {

    @Spy
    @InjectMocks
    private StoreDaoImpl dao;
    @Mock
    private StoreRepository storeRepository;

    @Test
    public void testCreateStore() throws UnknownStaffException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().address("address").build())
                .when(dao).queryAddress(any());
        doReturn(StaffEntity.builder().username("username").build())
                .when(dao).queryStaff(any());
        dao.createStore(getStore());

        verify(storeRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllStores() {
        dao.readAll();

        verify(storeRepository, times(1)).findAll();
    }

    private Store getStore() {
        return new Store(
                1,
                "staff",
                "address"
        );
    }

}
