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
                .store(queryStore(customer.getStore(), customer.getStaff(), customer.getStaffAddress(), customer.getStaffCity(), customer.getStaffCountry()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(queryAddress(customer.getAddress(), customer.getCity(), customer.getCountry()))
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

    protected StoreEntity queryStore(String store, String staff, String staffAddress, String staffCity, String staffCountry) throws UnknownStaffException, UnknownCountryException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(Integer.parseInt(store));
        if (!storeEntity.isPresent()) {
            Optional<StaffEntity> staffEntity = staffRepository.findByFirstName(staff);
            if (!staffEntity.isPresent()) {
                throw new UnknownStaffException(staff);
            }
            Optional<AddressEntity> staffAddressEntity = addressRepository.findByAddress(staffAddress);
            if (!staffAddressEntity.isPresent()) {
                Optional<CityEntity> staffCityEntity = cityRepository.findByName(staffCity);
                if (!staffCityEntity.isPresent()) {
                    Optional<CountryEntity> staffCountryEntity = countryRepository.findByName(staffCountry);
                    if (!staffCountryEntity.isPresent()) {
                        throw new UnknownCountryException(staffCountry);
                    }
                }
            }
            storeEntity = Optional.ofNullable(StoreEntity.builder()
                    .id(Integer.parseInt(store))
                    .staff(staffEntity.get())
                    .address(staffAddressEntity.get())
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            storeRepository.save(storeEntity.get());
            log.info("Recorded new Store: {}, {}, {}, {}, {}", store, staff, staffAddress, staffCity, staffCountry);
        }
        log.trace("Store Entity: {}", storeEntity);
        return storeEntity.get();
    }

    protected AddressEntity queryAddress(String address, String city, String country) throws UnknownCountryException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address);
        GeometryFactory geometryFactory = new GeometryFactory();
        if (!addressEntity.isPresent()) {
            Optional<CityEntity> cityEntity = cityRepository.findByName(city);
            if (!cityEntity.isPresent()) {
                Optional<CountryEntity> countryEntity = countryRepository.findByName(country);
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
                        entity.getStore().getStaff().getAddress().getAddress(),
                        entity.getStore().getStaff().getAddress().getCity().getName(),
                        entity.getStore().getStaff().getAddress().getCity().getCountry().getName(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getCity().getName(),
                        entity.getAddress().getCity().getCountry().getName()
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
                }
        ).findAny();
        if (!customerEntity.isPresent()) {
            throw new UnknownCustomerException(String.format("Customer Not Found %s", customer), customer);
        }
        customerRepository.delete(customerEntity.get());
    }

}

