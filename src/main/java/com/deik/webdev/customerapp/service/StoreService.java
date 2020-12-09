package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();
    Collection<Store> getStoresByStaff(String staff) throws UnknownStoreException, EmptyException;
    Store getStoreById(Integer id) throws UnknownStoreException, EmptyException, OutOfBoundsException;

    void recordStore(Store store) throws UnknownStaffException, UnknownAddressException;
    void deleteStore(Store store) throws UnknownStoreException;
    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException;

}
