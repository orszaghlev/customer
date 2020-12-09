package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;

import java.util.Collection;

public interface AddressDao {

    void createAddress(Address address) throws UnknownCityException;
    Collection<Address> readAll();
    Collection<Address> readAddressesByCity(String city) throws UnknownAddressException, EmptyException;
    Collection<Address> readAddressesByDistrict(String district) throws UnknownAddressException, EmptyException;
    Collection<Address> readAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException;
    Address readAddressById(Integer id) throws UnknownAddressException, EmptyException, OutOfBoundsException;

    void deleteAddress(Address address) throws UnknownAddressException;

    void updateAddress(Address address, Address newAddress) throws UnknownCityException, UnknownAddressException;

}
