package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StoreDao;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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
    public void testRecordStore() throws UnknownCountryException {
        Store store = getStore();
        service.recordStore(store);

        verify(dao, times(1)).createStore(store);
    }

    @Test
    void testRecordStoreWithUnknownCountry() throws UnknownCountryException {
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

    private Store getStore() {
        return new Store(
                "staff",
                "staffAddress",
                "staffCity",
                "staffCountry",
                "address",
                "city",
                "country"
        );
    }

    private Collection<Store> getDefaultStores() {
        return Arrays.asList(
                new Store(
                        "staff",
                        "staffAddress",
                        "staffCity",
                        "staffCountry",
                        "address",
                        "city",
                        "country"
                ),
                new Store(
                        "staff1",
                        "staffAddress1",
                        "staffCity1",
                        "staffCountry1",
                        "address1",
                        "city1",
                        "country1"
                ),
                new Store(
                        "staff2",
                        "staffAddress2",
                        "staffCity2",
                        "staffCountry2",
                        "address2",
                        "city2",
                        "country2"
                ));
    }

}
