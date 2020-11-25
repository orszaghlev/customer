package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> getAllStore();

    void recordStore(Store store) throws UnknownCountryException;
    void deleteStore(Store store) throws UnknownStoreException;

}
