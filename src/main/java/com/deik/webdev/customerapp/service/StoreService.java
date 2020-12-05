package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();

    void recordStore(Store store) throws UnknownStaffException, UnknownAddressException;
    void deleteStore(Store store) throws UnknownStoreException;
    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException;

}
