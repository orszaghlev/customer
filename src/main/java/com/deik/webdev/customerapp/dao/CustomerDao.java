package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    void createCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    Collection<Customer> readAll();
    Collection<Customer> readCustomersByName(String firstName, String lastName) throws UnknownCustomerException, OutOfBoundsException;
    Collection<Customer> readCustomersByEmail(String email) throws UnknownCustomerException;
    Collection<Customer> readCustomersByStoreId(Integer storeId) throws UnknownCustomerException, OutOfBoundsException;

    void deleteCustomer(Customer customer) throws UnknownCustomerException;

    void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException;

}
