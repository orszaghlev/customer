package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;

import java.util.Collection;

public interface AddressService {

    Collection<Address> getAllAddress();
    Collection<Address> getAddressesByCity(String city) throws UnknownAddressException, EmptyException;
    Collection<Address> getAddressesByDistrict(String district) throws UnknownAddressException, EmptyException;
    Collection<Address> getAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException;

    void recordAddress(Address address) throws UnknownCountryException;
    void deleteAddress(Address address) throws UnknownAddressException;
    void updateAddress(Address address, Address newAddress) throws UnknownCountryException, UnknownAddressException;

}
