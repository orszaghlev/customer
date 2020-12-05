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

        customerEntity = CustomerEntity.builder()
                .id(customer.getId())
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

    @Override
    public Collection<Customer> readAll() {
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
    }

    @Override
    public void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStoreException, UnknownAddressException, UnknownCustomerException, OutOfBoundsException {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customer.getId());
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        activeValue(newCustomer.getActive());
        log.info("Original: " + customerEntity.toString());
        customerEntity.get().setId(newCustomer.getId());
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

