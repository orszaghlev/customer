package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;
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
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final StaffRepository staffRepository;

    @Override
    public void createStore(Store store) throws UnknownStaffException, UnknownAddressException, OutOfBoundsException {
        StoreEntity storeEntity;
        correctValue(store.getId());
        correctValue(store.getStaffId());
        correctValue(store.getAddressId());

        storeEntity = StoreEntity.builder()
                .id(store.getId())
                .staff(queryStaff(store.getStaffId()))
                .address(queryAddress(store.getAddressId()))
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

    protected StaffEntity queryStaff(int staffId) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = staffRepository.findById(staffId);
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(String.valueOf(staffId));
        }
        else {
            log.trace("StaffEntity: {}", staffEntity);
            return staffEntity.get();
        }
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

    private void correctValue(int value) throws OutOfBoundsException {
        if (value < 0) {
            throw new OutOfBoundsException("Value can't be smaller than 0!");
        }
    }

    @Override
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> new Store(
                        entity.getId(),
                        entity.getStaff().getId(),
                        entity.getAddress().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStore(Store store) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = StreamSupport.stream(storeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return store.getId() == entity.getId() &&
                            store.getStaffId() == entity.getStaff().getId()  &&
                            store.getAddressId() == entity.getAddress().getId();
                }
        ).findAny();
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(String.format("Store Not Found %s", store));
        }
        storeRepository.delete(storeEntity.get());
    }

    @Override
    public void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(store.getId());
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException(String.format("Store Not Found %s", store), store);
        }
        correctValue(newStore.getId());
        correctValue(newStore.getStaffId());
        correctValue(newStore.getAddressId());
        log.info("Original: " + storeEntity.toString());
        storeEntity.get().setId(newStore.getId());
        storeEntity.get().setStaff(queryStaff(newStore.getStaffId()));
        storeEntity.get().setAddress(queryAddress(newStore.getAddressId()));
        storeEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + storeEntity.toString());
        try {
            storeRepository.save(storeEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
