package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Staff;
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

import static java.lang.Integer.parseInt;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffDaoImpl implements StaffDao {

    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public void createStaff(Staff staff) throws UnknownCountryException {
        StaffEntity staffEntity;

        staffEntity = StaffEntity.builder()
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .address(queryAddress(staff.getAddress(), staff.getCity(), staff.getCountry()))
                .email(staff.getEmail())
                .store(queryStore(staff.getStore(), staff.getStoreAddress(), staff.getStoreCity(), staff.getStoreCountry()))
                .username(staff.getUsername())
                .password(staff.getPassword())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("StaffEntity: {}", staffEntity);
        try {
            staffRepository.save(staffEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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

    protected StoreEntity queryStore(String store, String storeAddress, String storeCity, String storeCountry) throws UnknownCountryException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(parseInt(store));
        if (!storeEntity.isPresent()) {
            Optional<AddressEntity> storeAddressEntity = addressRepository.findByAddress(storeAddress);
            if (!storeAddressEntity.isPresent()) {
                Optional<CityEntity> storeCityEntity = cityRepository.findByName(storeCity);
                if (!storeCityEntity.isPresent()) {
                    Optional<CountryEntity> storeCountryEntity = countryRepository.findByName(storeCountry);
                    if (!storeCountryEntity.isPresent()) {
                        throw new UnknownCountryException(storeCountry);
                    }
                }
            }
            storeEntity = Optional.ofNullable(StoreEntity.builder()
                    .id(parseInt(store))
                    .address(storeAddressEntity.get())
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            storeRepository.save(storeEntity.get());
            log.info("Recorded new Store: {}, {}, {}, {}", store, storeAddress, storeCity, storeCountry);
        }
        log.trace("Store Entity: {}", storeEntity);
        return storeEntity.get();
    }

    @Override
    public Collection<Staff> readAll() {
        return StreamSupport.stream(staffRepository.findAll().spliterator(),false)
                .map(entity -> new Staff(
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getCity().getName(),
                        entity.getAddress().getCity().getCountry().getName(),
                        entity.getEmail(),
                        entity.getStore().getAddress().getAddress(),
                        entity.getStore().getStaff().getFirstName(),
                        entity.getStore().getAddress().getCity().getName(),
                        entity.getStore().getAddress().getCity().getCountry().getName(),
                        entity.getUsername(),
                        entity.getPassword()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStaff(Staff staff) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = StreamSupport.stream(staffRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return staff.getFirstName().equals(entity.getFirstName()) &&
                            staff.getLastName().equals(entity.getLastName()) &&
                            staff.getAddress().equals(entity.getAddress().getCity().getCountry()) &&
                            staff.getEmail().equals(entity.getEmail()) &&
                            staff.getStore().equals(entity.getStore().getAddress().getCity().getCountry()) &&
                            staff.getUsername().equals(entity.getUsername()) &&
                            staff.getPassword().equals(entity.getPassword());
                }
        ).findAny();
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(String.format("Staff Not Found %s", staff));
        }
        staffRepository.delete(staffEntity.get());
    }

}
