package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CustomerDto;
import com.deik.webdev.customerapp.dto.CustomerUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    @Test
    public void testListCustomers() {
        when(customerService.getAllCustomer()).thenReturn(getCustomers());
        customerController.listCustomers();

        verify(customerService, times(1)).getAllCustomer();
    }

    @Test
    public void testListCustomersByName() throws UnknownCustomerException, EmptyException {
        when(customerService.getCustomersByName(any(), any())).thenReturn(getCustomers());
        customerController.listCustomersByName(anyString(), anyString());

        verify(customerService, times(1)).getCustomersByName(anyString(), anyString());
    }

    @Test
    public void testListCustomersByEmail() throws UnknownCustomerException, EmptyException {
        when(customerService.getCustomersByEmail(any())).thenReturn(getCustomers());
        customerController.listCustomersByEmail(anyString());

        verify(customerService, times(1)).getCustomersByEmail(anyString());
    }

    @Test
    public void testListCustomersByStoreId() throws UnknownCustomerException, EmptyException {
        when(customerService.getCustomersByStoreId(any())).thenReturn(getCustomers());
        customerController.listCustomersByStoreId(anyInt());

        verify(customerService, times(1)).getCustomersByStoreId(anyInt());
    }

    @Test
    public void testListActiveCustomers() throws UnknownCustomerException, EmptyException, OutOfBoundsException {
        when(customerService.getActiveCustomers(any())).thenReturn(getCustomers());
        customerController.listActiveCustomers(anyInt());

        verify(customerService, times(1)).getActiveCustomers(anyInt());
    }

    @Test
    public void testListCustomerById() throws UnknownCustomerException, EmptyException, OutOfBoundsException {
        when(customerService.getCustomerById(any())).thenReturn(getCustomer());
        customerController.listCustomerById(anyInt());

        verify(customerService, times(1)).getCustomerById(anyInt());
    }

    @Test
    public void testRecordCustomer() throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        customerController.recordCustomer(getCustomerDto());

        verify(customerService, times(1)).recordCustomer(getCustomer());
    }

    @Test
    public void testDeleteCustomer() throws UnknownCustomerException {
        customerController.deleteCustomer(getCustomerDto());

        verify(customerService, times(1)).deleteCustomer(any());
    }

    @Test
    public void testUpdateCustomer() throws UnknownCustomerException, UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        customerController.updateCustomer(getCustomerUpdateRequestDto());

        verify(customerService, times(1)).updateCustomer(any(), any());
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

    private CustomerDto getCustomerDto() {
        return new CustomerDto(
                1,
                1,
                "firstName",
                "lastName",
                "email",
                1,
                0
        );
    }

    private CustomerUpdateRequestDto getCustomerUpdateRequestDto() {
        return new CustomerUpdateRequestDto(
                1,
                1,
                "firstName",
                "lastName",
                "email",
                1,
                0
        );
    }

    private Collection<Customer> getCustomers() {
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
