package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StoreEntity;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;
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
public class CustomerDaoImplTest {

    @Spy
    @InjectMocks
    private CustomerDaoImpl dao;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private StoreRepository storeRepository;

    @Test
    void testCreateCustomer() throws UnknownStaffException, UnknownCountryException {
        doReturn(AddressEntity.builder().address("47 MySakila Drive").build())
                .when(dao).queryAddress(any(),any(),any());
        doReturn(StoreEntity.builder().id(parseInt("1")).build())
                .when(dao).queryStore(any(),any(),any(),any(),any());
        dao.createCustomer(getCustomer());

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void deleteCustomer() throws UnknownCustomerException {
        doThrow(UnknownCustomerException.class).when(dao).deleteCustomer(any());

        assertThrows(UnknownCustomerException.class, ()->{
            dao.deleteCustomer(getCustomer());
        });
    }

    private Customer getCustomer() {
        return new Customer(
                "store",
                "staff",
                "staffAddress",
                "staffCity",
                "staffCountry",
                "firstName",
                "lastName",
                "email",
                "address",
                "city",
                "country"
        );
    }

}
