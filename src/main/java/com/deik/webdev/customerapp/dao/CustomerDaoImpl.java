package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
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
    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public void createCustomer(Customer customer) throws UnknownStaffException, UnknownCountryException {
        CustomerEntity customerEntity;

        customerEntity = CustomerEntity.builder()
                .store(queryStore(customer.getStore(), customer.getStaff()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(queryAddress(customer.getAddress(), customer.getCity(), customer.getCountry()))
                .active(Integer.parseInt(customer.getActive()))
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

    protected StoreEntity queryStore(String store, String staff) throws UnknownStaffException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(Integer.parseInt(store));
        if (!storeEntity.isPresent()) {
            Optional<StaffEntity> staffEntity = staffRepository.findByUsername(staff);
            if (!staffEntity.isPresent()) {
                throw new UnknownStaffException(staff);
            }
            storeEntity = Optional.ofNullable(StoreEntity.builder()
                    .id(Integer.parseInt(store))
                    .staff(staffEntity.get())
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            storeRepository.save(storeEntity.get());
            log.info("Recorded new Store: {}, {}", store, staff);
        }
        log.trace("Store Entity: {}", storeEntity);
        return storeEntity.get();
    }

    protected AddressEntity queryAddress(String address, String city, String country) throws UnknownCountryException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address);
        GeometryFactory geometryFactory = new GeometryFactory();
        if (!addressEntity.isPresent()) {
            Optional<CityEntity> cityEntity = cityRepository.findByCity(city);
            if (!cityEntity.isPresent()) {
                Optional<CountryEntity> countryEntity = countryRepository.findByCountry(country);
                if (!countryEntity.isPresent()) {
                    throw new UnknownCountryException(country);
                }
            }
            addressEntity = Optional.ofNullable(AddressEntity.builder()
                    .address(address)
                    .address2(address)
                    .district(address)
                    .city(cityEntity.get())
                    .postalCode(address)
                    .phone(address)
                    .location(geometryFactory.createPoint(new Coordinate()))
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            addressRepository.save(addressEntity.get());
            log.info("Recorded new Address: {}, {}, {}", address, city, country);
        }
        log.trace("Address Entity: {}", addressEntity);
        return addressEntity.get();
    }

    @Override
    public Collection<Customer> readAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(),false)
                .map(entity -> new Customer(
                        String.valueOf(entity.getStore().getId()),
                        entity.getStore().getStaff().getUsername(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getCity().getCity(),
                        entity.getAddress().getCity().getCountry().getCountry(),
                        String.valueOf(entity.getActive())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Customer customer) throws UnknownCustomerException {
        Optional<CustomerEntity> customerEntity = StreamSupport.stream(customerRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return customer.getStore().equals(entity.getStore().getStaff().getAddress().getCity().getCountry())  &&
                            customer.getFirstName().equals(entity.getFirstName()) &&
                            customer.getLastName().equals(entity.getLastName()) &&
                            customer.getEmail().equals(entity.getEmail()) &&
                            customer.getAddress().equals(entity.getAddress().getCity().getCountry());
                            //customer.getActive().equals(entity.getActive());
                }
        ).findAny();
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        customerRepository.delete(customerEntity.get());
    }

    @Override
    public void updateCustomer(Customer customer, Customer newCustomer) throws UnknownStaffException, UnknownCountryException, UnknownCustomerException {
        Optional<CustomerEntity> customerEntity = customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        log.info("Original: " + customerEntity.toString());
        customerEntity.get().setStore(queryStore(newCustomer.getStore(), newCustomer.getStaff()));
        customerEntity.get().setFirstName(newCustomer.getFirstName());
        customerEntity.get().setLastName(newCustomer.getLastName());
        customerEntity.get().setEmail(newCustomer.getEmail());
        customerEntity.get().setAddress(queryAddress(newCustomer.getAddress(), newCustomer.getCity(), newCustomer.getCountry()));
        customerEntity.get().setActive(Integer.parseInt(newCustomer.getActive()));
        customerEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + customerEntity.toString());
        try {
            customerRepository.save(customerEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

