package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StaffEntity;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;
import com.deik.webdev.customerapp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testCreateStore() throws UnknownStaffException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().id(1).build())
                .when(dao).queryAddress(anyInt());
        doReturn(StaffEntity.builder().id(1).build())
                .when(dao).queryStaff(anyInt());
        dao.createStore(getStore());

        verify(storeRepository, times(1)).save(any());
    }

    @Test
    void testReadAllStores() {
        dao.readAll();

        verify(storeRepository, times(1)).findAll();
    }

    @Test
    public void deleteStore() throws UnknownStoreException {
        doThrow(UnknownStoreException.class).when(dao).deleteStore(any());

        assertThrows(UnknownStoreException.class, ()->{
            dao.deleteStore(getStore());
        });
    }

    @Test
    public void updateStore() throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException {
        doThrow(UnknownStoreException.class).when(dao).updateStore(any(), any());

        assertThrows(UnknownStoreException.class, ()->{
            dao.updateStore(getStore(), getNewStore());
        });
    }

    private Store getStore() {
        return new Store(
                1,
                1,
                1
        );
    }

    private Store getNewStore() {
        return new Store(
                2,
                2,
                2
        );
    }

}
