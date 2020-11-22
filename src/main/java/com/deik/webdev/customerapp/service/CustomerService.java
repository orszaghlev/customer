package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> getAllCustomer();

    void createCustomer(Customer customer) throws UnknownStoreException;
    void deleteCustomer(Customer customer) throws UnknownCustomerException;

}
