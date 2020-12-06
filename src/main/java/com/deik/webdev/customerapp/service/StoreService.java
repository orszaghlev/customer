package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();
    Collection<Store> getStoresByStaffId(int staffId) throws UnknownStoreException;

    void recordStore(Store store) throws UnknownStaffException, UnknownAddressException, OutOfBoundsException;
    void deleteStore(Store store) throws UnknownStoreException;
    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException;

}
