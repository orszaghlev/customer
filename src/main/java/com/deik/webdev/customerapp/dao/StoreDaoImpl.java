package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;
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
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final StaffRepository staffRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public void createStore(Store store) throws UnknownCountryException {
        StoreEntity storeEntity;

        storeEntity = StoreEntity.builder()
                .id(parseInt(store.getId()))
                .staff(queryStaff(store.getStaff(), store.getStaffAddress(), store.getStaffCity(), store.getStaffCountry()))
                .address(queryAddress(store.getAddress(), store.getCity(), store.getCountry()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("StoreEntity: {}", storeEntity);
        try {
            storeRepository.save(storeEntity);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected StaffEntity queryStaff(String staff, String staffAddress, String staffCity, String staffCountry) throws UnknownCountryException {
        Optional<StaffEntity> staffEntity = staffRepository.findByFirstName(staff);
        if (!staffEntity.isPresent()) {
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
            staffEntity = Optional.ofNullable(StaffEntity.builder()
                    .firstName(staff)
                    .lastName(staff)
                    .address(staffAddressEntity.get())
                    .email(staff)
                    .username(staff)
                    .password(staff)
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            staffRepository.save(staffEntity.get());
            log.info("Recorded new Staff: {}, {}, {}, {}", staff, staffAddress, staffCity, staffCountry);
        }
        log.trace("Staff Entity: {}", staffEntity);
        return staffEntity.get();
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
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> new Store(
                        String.valueOf(entity.getId()),
                        entity.getStaff().getFirstName(),
                        entity.getStaff().getAddress().getAddress(),
                        entity.getStaff().getAddress().getCity().getName(),
                        entity.getStaff().getAddress().getCity().getCountry().getName(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getCity().getName(),
                        entity.getAddress().getCity().getCountry().getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStore(Store store) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = StreamSupport.stream(storeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return store.getId().equals(String.valueOf(entity.getId())) &&
                            store.getStaff().equals(entity.getStaff().getAddress().getCity().getCountry())  &&
                            store.getAddress().equals(entity.getAddress().getCity().getCountry());
                }
        ).findAny();
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(String.format("Store Not Found %s", store), store);
        }
        storeRepository.delete(storeEntity.get());
    }

}
