package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StoreDao;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;
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
public class StoreServiceImplTest {

    @InjectMocks
    private StoreServiceImpl service;
    @Mock
    private StoreDao dao;

    @Test
    public void testRecordStore() throws UnknownStaffException, UnknownCountryException {
        Store store = getStore();
        service.recordStore(store);

        verify(dao, times(1)).createStore(store);
    }

    @Test
    void testRecordStoreWithUnknownCountry() throws UnknownStaffException, UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createStore(any());

        assertThrows(UnknownCountryException.class, ()->{
            service.recordStore(getStore());
        });
    }

    @Test
    void testReadAllStores() {
        when(dao.readAll()).thenReturn(getDefaultStores());
        Collection<Store> actual = service.getAllStore();

        assertThat(getDefaultStores(), is(actual));
    }

    @Test
    void testDeleteStore() throws UnknownStoreException {
        Store store = getStore();
        service.deleteStore(store);

        verify(dao, times(1)).deleteStore(store);
    }

    private Store getStore() {
        return new Store(
                "1",
                "staff",
                "address",
                "city",
                "country"
        );
    }

    private Collection<Store> getDefaultStores() {
        return Arrays.asList(
                new Store(
                        "1",
                        "staff",
                        "address",
                        "city",
                        "country"
                ),
                new Store(
                        "2",
                        "staff1",
                        "address1",
                        "city1",
                        "country1"
                ),
                new Store(
                        "3",
                        "staff2",
                        "address2",
                        "city2",
                        "country2"
                ));
    }

}
