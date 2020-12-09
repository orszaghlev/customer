package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CustomerDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
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
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;
    @Mock
    private CustomerDao dao;

    @Test
    public void testRecordCustomer() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        Customer customer = getCustomer();
        service.recordCustomer(customer);

        verify(dao, times(1)).createCustomer(customer);
    }

    @Test
    public void testRecordCustomerWithUnknownStore() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doThrow(UnknownStoreException.class).when(dao).createCustomer(any());

        assertThrows(UnknownStoreException.class, ()->{
            service.recordCustomer(getCustomer());
        });
    }

    @Test
    public void testRecordCustomerWithUnknownAddress() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        doThrow(UnknownAddressException.class).when(dao).createCustomer(any());

        assertThrows(UnknownAddressException.class, ()->{
            service.recordCustomer(getCustomer());
        });
    }

    @Test
    public void testReadAllCustomers() {
        when(dao.readAll()).thenReturn(getDefaultCustomers());
        Collection<Customer> actual = service.getAllCustomer();

        assertThat(getDefaultCustomers(), is(actual));
    }

    @Test
    public void testReadCustomersFromName() throws UnknownCustomerException, EmptyException {
        final String firstName = "firstName";
        final String lastName = "lastName";
        Collection<Customer> actual = service.getCustomersByName(firstName, lastName);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadCustomersFromEmail() throws UnknownCustomerException, EmptyException {
        final String email = "email";
        Collection<Customer> actual = service.getCustomersByEmail(email);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadCustomersFromStoreId() throws UnknownCustomerException, EmptyException {
        final Integer storeId = 1;
        Collection<Customer> actual = service.getCustomersByStoreId(storeId);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadActiveCustomers() throws UnknownCustomerException, OutOfBoundsException, EmptyException {
        final Integer active = 1;
        Collection<Customer> actual = service.getActiveCustomers(active);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadCustomerById() throws UnknownCustomerException, OutOfBoundsException, EmptyException {
        final Integer id = 1;
        Customer actual = service.getCustomerById(id);

        verify(dao, times(1)).readCustomerById(id);
    }

    @Test
    public void testDeleteCustomer() throws UnknownCustomerException {
        Customer customer = getCustomer();
        service.deleteCustomer(customer);

        verify(dao, times(1)).deleteCustomer(customer);
    }

    @Test
    public void testUpdateCustomer() throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException {
        Customer customer = getCustomer();
        Customer newCustomer = getNewCustomer();
        service.updateCustomer(customer, newCustomer);

        verify(dao, times(1)).updateCustomer(customer, newCustomer);
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

    private Collection<Customer> getDefaultCustomers() {
        return Arrays.asList(
                new Customer(
                        1,
                        1,
                        "firstName",
                        "lastName",
                        "email",
                        1,
                        0
                ),
                new Customer(
                        2,
                        2,
                        "firstName",
                        "lastName",
                        "email",
                        2,
                        1
                ),
                new Customer(
                        3,
                        3,
                        "firstName",
                        "lastName",
                        "email",
                        3,
                        0
                ));
    }

}
