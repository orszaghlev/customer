package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.StoreEntity;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
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
public class CustomerDaoImplTest {

    @Spy
    @InjectMocks
    private CustomerDaoImpl dao;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomer() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().address("address").build())
                .when(dao).queryAddress(any());
        doReturn(StoreEntity.builder().id(1).build())
                .when(dao).queryStore(anyInt());
        dao.createCustomer(getCustomer());

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllCustomers() {
        dao.readAll();

        verify(customerRepository, times(1)).findAll();
    }

    private Customer getCustomer() {
        return new Customer(
                1,
                1,
                "firstName",
                "lastName",
                "email",
                "address",
                0
        );
    }

}
