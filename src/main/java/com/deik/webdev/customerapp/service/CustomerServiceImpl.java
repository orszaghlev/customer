package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CustomerDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    public Collection<Customer> getAllCustomer() {
        return customerDao.readAll();
    }

    @Override
    public Collection<Customer> getCustomersByName(String firstName, String lastName) throws UnknownCustomerException, EmptyException {
        return customerDao.readCustomersByName(firstName, lastName);
    }

    @Override
    public Collection<Customer> getCustomersByEmail(String email) throws UnknownCustomerException, EmptyException {
        return customerDao.readCustomersByEmail(email);
    }

    @Override
    public Collection<Customer> getCustomersByStoreId(Integer storeId) throws UnknownCustomerException, EmptyException, OutOfBoundsException {
        return customerDao.readCustomersByStoreId(storeId);
    }

    @Override
    public Collection<Customer> getActiveCustomers(Integer active) throws UnknownCustomerException, OutOfBoundsException, EmptyException {
        return customerDao.readActiveCustomers(active);
    }

    @Override
    public Customer getCustomerById(Integer id) throws UnknownCustomerException, OutOfBoundsException, EmptyException {
        return customerDao.readCustomerById(id);
    }

    @Override
    public void recordCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        customerDao.createCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) throws UnknownCustomerException {
        customerDao.deleteCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException {
        customerDao.updateCustomer(customer, newCustomer);
    }

}
