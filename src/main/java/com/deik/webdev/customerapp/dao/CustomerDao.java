package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    void createCustomer(Customer customer) throws UnknownStoreException;
    Collection<Customer> readAll();

    void deleteCustomer(Customer customer) throws UnknownCustomerException;

}
