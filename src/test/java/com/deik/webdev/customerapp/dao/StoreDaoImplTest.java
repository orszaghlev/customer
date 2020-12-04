package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StaffEntity;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;

    @Test
    void testCreateStore() throws UnknownStaffException, UnknownCountryException {
        doReturn(AddressEntity.builder().address("47 MySakila Drive").build())
                .when(dao).queryAddress(any(),any(),any());
        doReturn(StaffEntity.builder().firstName("Mike").build())
                .when(dao).queryStaff(any());
        dao.createStore(getStore());

        verify(storeRepository, times(1)).save(any());
    }

    @Test
    public void deleteStore() throws UnknownStoreException {
        doThrow(UnknownStoreException.class).when(dao).deleteStore(any());

        assertThrows(UnknownStoreException.class, ()->{
            dao.deleteStore(getStore());
        });
    }

    @Test
    public void updateStore() throws UnknownStaffException, UnknownCountryException, UnknownStoreException {
        doThrow(UnknownStoreException.class).when(dao).updateStore(any(), any());

        assertThrows(UnknownStoreException.class, ()->{
            dao.updateStore(getStore(), getNewStore());
        });
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

    private Store getNewStore() {
        return new Store(
                "2",
                "newStaff",
                "newAddress",
                "newCity",
                "newCountry"
        );
    }

}
