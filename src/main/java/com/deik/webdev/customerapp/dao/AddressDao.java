package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;

import java.util.Collection;

public interface AddressDao {

    void createAddress(Address address) throws UnknownCountryException;
    Collection<Address> readAll();
    Collection<Address> readAddressesByCity(String city) throws UnknownAddressException, EmptyException;
    Collection<Address> readAddressesByDistrict(String district) throws UnknownAddressException, EmptyException;
    Collection<Address> readAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException;

    void deleteAddress(Address address) throws UnknownAddressException;

    void updateAddress(Address address, Address newAddress) throws UnknownCountryException, UnknownAddressException;

}
