package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CustomerDao;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
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
    public void recordCustomer(Customer customer) throws UnknownStoreException {
        customerDao.createCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) throws UnknownCustomerException {
        customerDao.deleteCustomer(customer);
    }

}
