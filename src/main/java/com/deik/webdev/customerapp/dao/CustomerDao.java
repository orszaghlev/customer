package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    void createCustomer(Customer customer) throws UnknownStaffException, UnknownCountryException;
    Collection<Customer> readAll();

    void deleteCustomer(Customer customer) throws UnknownCustomerException;

}
