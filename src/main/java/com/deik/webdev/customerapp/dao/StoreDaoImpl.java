package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
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
    public void createStore(Store store) throws UnknownStaffException, UnknownCountryException {
        StoreEntity storeEntity;

        storeEntity = StoreEntity.builder()
                .id(Integer.parseInt(store.getId()))
                .staff(queryStaff(store.getStaff()))
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

    protected StaffEntity queryStaff(String staff) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = staffRepository.findByUsername(staff);
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(staff);
        }
        else {
            staffEntity = Optional.ofNullable(StaffEntity.builder()
                    .firstName(staff)
                    .lastName(staff)
                    .email(staff)
                    .username(staff)
                    .password(staff)
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            staffRepository.save(staffEntity.get());
            log.info("Recorded new Staff: {}", staff);
        }
        log.trace("Staff Entity: {}", staffEntity);
        return staffEntity.get();
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
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> new Store(
                        String.valueOf(entity.getId()),
                        entity.getStaff().getUsername(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getCity().getCity(),
                        entity.getAddress().getCity().getCountry().getCountry()
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
            throw new UnknownStoreException(String.format("Store Not Found %s", store));
        }
        storeRepository.delete(storeEntity.get());
    }

    @Override
    public void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownCountryException, UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(Integer.parseInt(store.getId()));
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(String.format("Store Not Found %s", store), store);
        }
        log.info("Original: " + storeEntity.toString());
        storeEntity.get().setId(Integer.parseInt(newStore.getId()));
        storeEntity.get().setStaff(queryStaff(newStore.getStaff()));
        storeEntity.get().setAddress(queryAddress(newStore.getAddress(), newStore.getCity(), newStore.getCountry()));
        storeEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + storeEntity.toString());
        try {
            storeRepository.save(storeEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
