package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.StoreDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;

    @Override
    public Collection<Store> getAllStore() {
        return storeDao.readAll();
    }

    @Override
    public Collection<Store> getStoresByStaff(String staff) throws UnknownStoreException, EmptyException {
        return storeDao.readStoresByStaff(staff);
    }

    @Override
    public Store getStoreById(Integer id) throws UnknownStoreException, EmptyException, OutOfBoundsException {
        return storeDao.readStoreById(id);
    }

    @Override
    public void recordStore(Store store) throws UnknownStaffException, UnknownAddressException {
        storeDao.createStore(store);
    }

    @Override
    public void deleteStore(Store store) throws UnknownStoreException {
        storeDao.deleteStore(store);
    }

    @Override
    public void updateStore(Store store, Store newStore) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, OutOfBoundsException {
        storeDao.updateStore(store, newStore);
    }

}
