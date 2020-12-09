package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    void createCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException;
    Collection<Customer> readAll();
    Collection<Customer> readCustomersByName(String firstName, String lastName) throws UnknownCustomerException, EmptyException;
    Collection<Customer> readCustomersByEmail(String email) throws UnknownCustomerException, EmptyException;
    Collection<Customer> readCustomersByStoreId(Integer storeId) throws UnknownCustomerException, EmptyException;
    Collection<Customer> readActiveCustomers(Integer active) throws UnknownCustomerException, OutOfBoundsException, EmptyException;
    Customer readCustomerById(Integer id) throws UnknownCustomerException, OutOfBoundsException, EmptyException;

    void deleteCustomer(Customer customer) throws UnknownCustomerException;

    void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException;

}
