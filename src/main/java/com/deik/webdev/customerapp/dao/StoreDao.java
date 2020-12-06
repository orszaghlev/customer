package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreDao {

    void createStore(Store store) throws UnknownStaffException, UnknownAddressException, OutOfBoundsException;
    Collection<Store> readAll();
    Collection<Store> readStoresByStaffId(Integer staffId) throws UnknownStoreException, OutOfBoundsException;

    void deleteStore(Store store) throws UnknownStoreException;

    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException;

}
