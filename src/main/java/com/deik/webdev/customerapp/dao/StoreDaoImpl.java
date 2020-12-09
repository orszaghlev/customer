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
    public void createStore(Store store) throws UnknownStaffException, UnknownAddressException {
        StoreEntity storeEntity;

        storeEntity = StoreEntity.builder()
                .staff(queryStaff(store.getStaff()))
                .address(queryAddress(store.getAddress()))
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

    protected StaffEntity queryStaff(String staff) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = staffRepository.findByUsername(staff);
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.trace("StaffEntity: {}", staffEntity);
            return staffEntity.get();
        }
    }

    protected AddressEntity queryAddress(String address) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address);
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException("No Address Found");
        }
        else {
            log.trace("AddressEntity: {}", addressEntity);
            return addressEntity.get();
        }
    }

    private void correctValue(int value) throws OutOfBoundsException {
        if (value <= 0) {
            throw new OutOfBoundsException("ID can't be less than 1!");
        }
    }

    @Override
    public Collection<Store> readAll() {
        log.info("Read all stores");
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> new Store(
                        entity.getId(),
                        entity.getStaff().getUsername(),
                        entity.getAddress().getAddress()
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
                            storeEntity.get().getStaff().getUsername(),
                            storeEntity.get().getAddress().getAddress()
                    );
        }
    }

    @Override
    public Collection<Store> readStoresByStaff(String staff) throws UnknownStoreException, EmptyException {
        if (staff == null) {
            throw new EmptyException("Add a staff username!");
        }
        Optional<StaffEntity> staffEntity = staffRepository.findByUsername(staff);
        Collection<StoreEntity> storeEntity = storeRepository.findByStaff(staffEntity);
        if (storeEntity.isEmpty()) {
            throw new UnknownStoreException("No Stores Found");
        }
        else {
            log.info("Read all stores (by staff username)");
            return StreamSupport.stream(storeRepository.findByStaff(staffEntity).spliterator(),false)
                    .map(entity -> new Store(
                            entity.getId(),
                            entity.getStaff().getUsername(),
                            entity.getAddress().getAddress()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deleteStore(Store store) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = StreamSupport.stream(storeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return store.getId() == entity.getId() &&
                            store.getStaff().equals(entity.getStaff().getUsername()) &&
                            store.getAddress().equals(entity.getAddress().getAddress());
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
        log.info("Original: " + storeEntity.toString());
        storeEntity.get().setStaff(queryStaff(newStore.getStaff()));
        storeEntity.get().setAddress(queryAddress(newStore.getAddress()));
        storeEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + storeEntity.toString());
        try {
            storeRepository.save(storeEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
