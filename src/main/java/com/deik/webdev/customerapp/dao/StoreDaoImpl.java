package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.*;
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
            log.info("Recorded new Store: {}", store);
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
        log.info("Read all stores");
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> new Store(
                        entity.getId(),
                        entity.getStaff().getId(),
                        entity.getAddress().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Store readStoreById(Integer id) throws UnknownStoreException, EmptyException, OutOfBoundsException {
        if (id == null) {
            throw new EmptyException("Add an ID!");
        }
        correctValue(id);
        Optional<StoreEntity> storeEntity = storeRepository.findById(id);
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException("No Store Found");
        }
        else {
            log.info("Read store (by ID)");
            return new Store(
                            storeEntity.get().getId(),
                            storeEntity.get().getStaff().getId(),
                            storeEntity.get().getAddress().getId()
                    );
        }
    }

    @Override
    public Collection<Store> readStoresByStaffId(Integer staffId) throws UnknownStoreException, EmptyException {
        if (staffId == null) {
            throw new EmptyException("Add a staff ID!");
        }
        Collection<StoreEntity> storeEntity = storeRepository.findByStaffId(staffId);
        if (storeEntity.isEmpty()) {
            throw new UnknownStoreException("No Stores Found");
        }
        else {
            log.info("Read all stores (by staff ID)");
            return StreamSupport.stream(storeRepository.findByStaffId(staffId).spliterator(),false)
                    .map(entity -> new Store(
                            entity.getId(),
                            entity.getStaff().getId(),
                            entity.getAddress().getId()
                    ))
                    .collect(Collectors.toList());
        }
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
        log.info("Deleted store: " + storeEntity.toString());
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
