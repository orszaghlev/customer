package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;

import java.util.Collection;

public interface AddressService {

    Collection<Address> getAllAddress();
    Collection<Address> getAddressesByCity(String city) throws UnknownAddressException, EmptyException;
    Collection<Address> getAddressesByDistrict(String district) throws UnknownAddressException, EmptyException;
    Collection<Address> getAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException;
    Address getAddressById(Integer id) throws UnknownAddressException, EmptyException, OutOfBoundsException;

    void recordAddress(Address address) throws UnknownCityException;
    void deleteAddress(Address address) throws UnknownAddressException;
    void updateAddress(Address address, Address newAddress) throws UnknownCityException, UnknownAddressException;

}
