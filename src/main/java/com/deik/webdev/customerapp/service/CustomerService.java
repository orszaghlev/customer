package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomer();

    void recordCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException;
    void deleteCustomer(Customer customer) throws UnknownCustomerException;
    void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException;

}
