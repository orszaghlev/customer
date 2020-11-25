package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreDao {

    void createStore(Store store) throws UnknownCountryException;
    Collection<Store> readAll();

    void deleteStore(Store store) throws UnknownStoreException;

}
