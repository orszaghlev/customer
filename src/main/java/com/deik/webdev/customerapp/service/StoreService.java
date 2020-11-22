package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();

    void createStore(Store store) throws UnknownStaffException;
    void deleteStore(Store store) throws UnknownStoreException;

}
