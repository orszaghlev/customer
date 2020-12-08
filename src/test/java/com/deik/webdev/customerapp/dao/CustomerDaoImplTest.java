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

    @Test
    void testCreateCustomer() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doReturn(AddressEntity.builder().id(1).build())
                .when(dao).queryAddress(anyInt());
        doReturn(StoreEntity.builder().id(1).build())
                .when(dao).queryStore(anyInt());
        dao.createCustomer(getCustomer());

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void testReadAllCustomers() {
        dao.readAll();

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void deleteCustomer() throws UnknownCustomerException {
        doThrow(UnknownCustomerException.class).when(dao).deleteCustomer(any());

        assertThrows(UnknownCustomerException.class, ()->{
            dao.deleteCustomer(getCustomer());
        });
    }

    @Test
    public void updateCustomer() throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException {
        doThrow(UnknownCustomerException.class).when(dao).updateCustomer(any(), any());

        assertThrows(UnknownCustomerException.class, ()->{
            dao.updateCustomer(getCustomer(), getNewCustomer());
        });
    }

    private Customer getCustomer() {
        return new Customer(
                1,
                1,
                "firstName",
                "lastName",
                "email",
                1,
                0
        );
    }

    private Customer getNewCustomer() {
        return new Customer(
                2,
                2,
                "newFirstName",
                "newLastName",
                "newEmail",
                2,
                1
        );
    }

}
