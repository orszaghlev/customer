package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;

import java.util.Collection;

public interface StoreDao {

    void createStore(Store store) throws UnknownStaffException, UnknownAddressException;
    Collection<Store> readAll();
    Collection<Store> readStoresByStaff(String staff) throws UnknownStoreException, EmptyException;
    Store readStoreById(Integer id) throws UnknownStoreException, EmptyException, OutOfBoundsException;

    void deleteStore(Store store) throws UnknownStoreException;

    void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException;

}
