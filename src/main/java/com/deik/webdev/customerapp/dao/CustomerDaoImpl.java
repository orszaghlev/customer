package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;

    @Override
    public void createCustomer(Customer customer) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        CustomerEntity customerEntity;
        activeValue(customer.getActive());
        correctValue(customer.getId());
        correctValue(customer.getStoreId());
        correctValue(customer.getAddressId());

        customerEntity = CustomerEntity.builder()
                .store(queryStore(customer.getStoreId()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(queryAddress(customer.getAddressId()))
                .active(customer.getActive())
                .createDate(new Timestamp((new Date()).getTime()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CustomerEntity: {}", customerEntity);
        try {
            customerRepository.save(customerEntity);
            log.info("Recorded new Customer: {}", customer);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected StoreEntity queryStore(int storeId) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(String.valueOf(storeId));
        }
        log.trace("StoreEntity: {}", storeEntity);
        return storeEntity.get();
    }

    protected AddressEntity queryAddress(int addressId) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException(String.valueOf(addressId));
        }
        else {
            log.trace("AddressEntity: {}", addressEntity);
            return addressEntity.get();
        }
    }

    private void activeValue(int active) throws OutOfBoundsException {
        if (active != 0 && active != 1) {
            throw new OutOfBoundsException("Value of 'active' must be 0 or 1!");
        }
    }

    private void correctValue(int value) throws OutOfBoundsException {
        if (value < 0) {
            throw new OutOfBoundsException("Value can't be smaller than 0!");
        }
    }

    @Override
    public Collection<Customer> readAll() {
        log.info("Read all customers");
        return StreamSupport.stream(customerRepository.findAll().spliterator(),false)
                .map(entity -> new Customer(
                        entity.getId(),
                        entity.getStore().getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getId(),
                        entity.getActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Customer> readCustomersByName(String firstName, String lastName) throws UnknownCustomerException, EmptyException {
        if (firstName == null && lastName == null) {
            throw new EmptyException("Add a first name and a last name!");
        }
        if ((firstName != null && lastName == null) || (firstName == null && lastName != null)) {
            throw new EmptyException("You forgot to add a first name or a last name!");
        }
        Collection<CustomerEntity> customerEntity = customerRepository.findByFirstNameAndLastName(firstName, lastName);
        if (customerEntity.isEmpty()) {
            throw new UnknownCustomerException("No Customers Found");
        }
        else {
            log.info("Read all customers (by name)");
            return StreamSupport.stream(customerRepository.findByFirstNameAndLastName(firstName, lastName).spliterator(),false)
                    .map(entity -> new Customer(
                            entity.getId(),
                            entity.getStore().getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getAddress().getId(),
                            entity.getActive()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Customer> readCustomersByEmail(String email) throws UnknownCustomerException, EmptyException {
        if (email == null) {
            throw new EmptyException("Add an email!");
        }
        Collection<CustomerEntity> customerEntity = customerRepository.findByEmail(email);
        if (customerEntity.isEmpty()) {
            throw new UnknownCustomerException("No Customers Found");
        }
        else {
            log.info("Read all customers (by email)");
            return StreamSupport.stream(customerRepository.findByEmail(email).spliterator(),false)
                    .map(entity -> new Customer(
                            entity.getId(),
                            entity.getStore().getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getAddress().getId(),
                            entity.getActive()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Customer> readCustomersByStoreId(Integer storeId) throws UnknownCustomerException, EmptyException {
        if (storeId == null) {
            throw new EmptyException("Add a store ID!");
        }
        Collection<CustomerEntity> customerEntity = customerRepository.findByStoreId(storeId);
        if (customerEntity.isEmpty()) {
            throw new UnknownCustomerException("No Customers Found");
        }
        else {
            log.info("Read all customers (by store ID)");
            return StreamSupport.stream(customerRepository.findByStoreId(storeId).spliterator(),false)
                    .map(entity -> new Customer(
                            entity.getId(),
                            entity.getStore().getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getAddress().getId(),
                            entity.getActive()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Customer> readActiveCustomers(Integer active) throws UnknownCustomerException, OutOfBoundsException, EmptyException {
        if (active == null) {
            throw new EmptyException("Add 0 (inactive) or 1 (active)!");
        }
        activeValue(active);
        Collection<CustomerEntity> customerEntity = customerRepository.findByActive(active);
        if (customerEntity.isEmpty()) {
            throw new UnknownCustomerException("No Customers Found");
        }
        else {
            log.info("Read all customers (by store ID)");
            return StreamSupport.stream(customerRepository.findByActive(active).spliterator(),false)
                    .map(entity -> new Customer(
                            entity.getId(),
                            entity.getStore().getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getAddress().getId(),
                            entity.getActive()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Customer readCustomerById(Integer id) throws UnknownCustomerException, EmptyException, OutOfBoundsException {
        if (id == null) {
            throw new EmptyException("Add an ID!");
        }
        correctValue(id);
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException("No Customer Found");
        }
        else {
            log.info("Read customer (by ID)");
            return new Customer(
                    customerEntity.get().getId(),
                    customerEntity.get().getStore().getId(),
                    customerEntity.get().getFirstName(),
                    customerEntity.get().getLastName(),
                    customerEntity.get().getEmail(),
                    customerEntity.get().getAddress().getId(),
                    customerEntity.get().getActive()
            );
        }
    }

    @Override
    public void deleteCustomer(Customer customer) throws UnknownCustomerException {
        Optional<CustomerEntity> customerEntity = StreamSupport.stream(customerRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return customer.getId() == entity.getId() &&
                            customer.getStoreId() == entity.getStore().getId()  &&
                            customer.getFirstName().equals(entity.getFirstName()) &&
                            customer.getLastName().equals(entity.getLastName()) &&
                            customer.getEmail().equals(entity.getEmail()) &&
                            customer.getAddressId() == entity.getAddress().getId() &&
                            customer.getActive() == entity.getActive();
                }
        ).findAny();
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        customerRepository.delete(customerEntity.get());
        log.info("Deleted customer: " + customerEntity.toString());
    }

    @Override
    public void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customer.getId());
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        activeValue(newCustomer.getActive());
        correctValue(newCustomer.getId());
        correctValue(newCustomer.getStoreId());
        correctValue(newCustomer.getAddressId());
        log.info("Original: " + customerEntity.toString());
        customerEntity.get().setStore(queryStore(newCustomer.getStoreId()));
        customerEntity.get().setFirstName(newCustomer.getFirstName());
        customerEntity.get().setLastName(newCustomer.getLastName());
        customerEntity.get().setEmail(newCustomer.getEmail());
        customerEntity.get().setAddress(queryAddress(newCustomer.getAddressId()));
        customerEntity.get().setActive(newCustomer.getActive());
        customerEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + customerEntity.toString());
        try {
            customerRepository.save(customerEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

