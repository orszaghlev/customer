package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();
    Collection<Store> getStoresByStaffId(Integer staffId) throws UnknownStoreException, EmptyException;

    void recordStore(Store store) throws UnknownStaffException, UnknownAddressException, OutOfBoundsException;
    void deleteStore(Store store) throws UnknownStoreException;
    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException;

}
