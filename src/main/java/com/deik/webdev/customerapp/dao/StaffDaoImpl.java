package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
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
    public void createStaff(Staff staff) throws UnknownStoreException, UnknownCountryException {
        StaffEntity staffEntity;

        staffEntity = StaffEntity.builder()
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .address(queryAddress(staff.getAddress(), staff.getCity(), staff.getCountry()))
                .email(staff.getEmail())
                .store(queryStore(staff.getStore()))
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

    protected StoreEntity queryStore(String store) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(Integer.parseInt(store));
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(store);
        }
        else {
            storeEntity = Optional.ofNullable(StoreEntity.builder()
                    .id(Integer.parseInt(store))
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            storeRepository.save(storeEntity.get());
            log.info("Recorded new Store: {}", store);
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
                        entity.getAddress().getCity().getCity(),
                        entity.getAddress().getCity().getCountry().getCountry(),
                        entity.getEmail(),
                        String.valueOf(entity.getStore().getId()),
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

    @Override
    public void updateStaff(Staff staff, Staff newStaff) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = staffRepository.findByUsername(staff.getUsername());
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(String.format("Staff Not Found %s", staff), staff);
        }
        log.info("Original: " + staffEntity.toString());
        staffEntity.get().setFirstName(newStaff.getFirstName());
        staffEntity.get().setLastName(newStaff.getLastName());
        staffEntity.get().setEmail(newStaff.getEmail());
        staffEntity.get().setUsername(newStaff.getUsername());
        staffEntity.get().setPassword(newStaff.getPassword());
        staffEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + staffEntity.toString());
        try {
            staffRepository.save(staffEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
