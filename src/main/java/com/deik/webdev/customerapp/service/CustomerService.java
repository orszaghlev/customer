package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomer();
    Collection<Customer> getCustomersByName(String firstName, String lastName) throws UnknownCustomerException;
    Collection<Customer> getCustomersByEmail(String email) throws UnknownCustomerException;

    void recordCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    void deleteCustomer(Customer customer) throws UnknownCustomerException;
    void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException;

}
