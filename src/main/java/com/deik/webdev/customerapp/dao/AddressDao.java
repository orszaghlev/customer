package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;

import java.util.Collection;

public interface AddressDao {

    void createAddress(Address address) throws UnknownCountryException;
    Collection<Address> readAll();

    void deleteAddress(Address address) throws UnknownAddressException;

}
