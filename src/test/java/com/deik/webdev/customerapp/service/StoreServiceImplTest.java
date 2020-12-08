package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StoreDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;
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
public class StoreServiceImplTest {

    @InjectMocks
    private StoreServiceImpl service;
    @Mock
    private StoreDao dao;

    @Test
    public void testRecordStore() throws UnknownStaffException, UnknownAddressException, OutOfBoundsException {
        Store store = getStore();
        service.recordStore(store);

        verify(dao, times(1)).createStore(store);
    }

    @Test
    public void testRecordStoreWithUnknownAddress() throws UnknownStaffException, UnknownAddressException, OutOfBoundsException {
        doThrow(UnknownAddressException.class).when(dao).createStore(any());

        assertThrows(UnknownAddressException.class, ()->{
            service.recordStore(getStore());
        });
    }

    @Test
    public void testReadAllStores() {
        when(dao.readAll()).thenReturn(getDefaultStores());
        Collection<Store> actual = service.getAllStore();

        assertThat(getDefaultStores(), is(actual));
    }

    @Test
    public void testReadStoresByStaffId() throws UnknownStoreException, EmptyException {
        final Integer staffId = 1;
        Collection<Store> actual = service.getStoresByStaffId(staffId);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testDeleteStore() throws UnknownStoreException {
        Store store = getStore();
        service.deleteStore(store);

        verify(dao, times(1)).deleteStore(store);
    }

    @Test
    public void testUpdateStore() throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException {
        Store store = getStore();
        Store newStore = getNewStore();
        service.updateStore(store, newStore);

        verify(dao, times(1)).updateStore(store, newStore);
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

    private Collection<Store> getDefaultStores() {
        return Arrays.asList(
                new Store(
                        1,
                        1,
                        1
                ),
                new Store(
                        2,
                        2,
                        2
                ),
                new Store(
                        3,
                        3,
                        3
                ));
    }

}
