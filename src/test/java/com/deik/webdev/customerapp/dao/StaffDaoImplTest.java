package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StoreEntity;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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

import static java.lang.Integer.parseInt;
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
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private StoreRepository storeRepository;

    @Test
    void testCreateStaff() throws UnknownStoreException, UnknownCountryException {
        doReturn(AddressEntity.builder().address("47 MySakila Drive").build())
                .when(dao).queryAddress(any(),any(),any());
        doReturn(StoreEntity.builder().id(parseInt("1")).build())
                .when(dao).queryStore(any());
        dao.createStaff(getStaff());

        verify(staffRepository, times(1)).save(any());
    }

    @Test
    public void deleteStaff() throws UnknownStaffException {
        doThrow(UnknownStaffException.class).when(dao).deleteStaff(any());

        assertThrows(UnknownStaffException.class, ()->{
            dao.deleteStaff(getStaff());
        });
    }

    @Test
    public void updateStaff() throws UnknownStoreException, UnknownCountryException, UnknownStaffException {
        doThrow(UnknownStaffException.class).when(dao).updateStaff(any(), any());

        assertThrows(UnknownStaffException.class, ()->{
            dao.updateStaff(getStaff(), getNewStaff());
        });
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

}
