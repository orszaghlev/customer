package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.AddressDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public Collection<Address> getAllAddress() {
        return addressDao.readAll();
    }

    @Override
    public Collection<Address> getAddressesByCity(String city) throws UnknownAddressException, EmptyException {
        return addressDao.readAddressesByCity(city);
    }

    @Override
    public Collection<Address> getAddressesByDistrict(String district) throws UnknownAddressException, EmptyException {
        return addressDao.readAddressesByDistrict(district);
    }

    @Override
    public Collection<Address> getAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException {
        return addressDao.readAddressesByPostalCode(postalCode);
    }

    @Override
    public Address getAddressById(Integer id) throws UnknownAddressException, EmptyException, OutOfBoundsException {
        return addressDao.readAddressById(id);
    }

    @Override
    public void recordAddress(Address address) throws UnknownCityException {
        addressDao.createAddress(address);
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException {
        addressDao.deleteAddress(address);
    }

    @Override
    public void updateAddress(Address address, Address newAddress) throws UnknownCityException, UnknownAddressException {
        addressDao.updateAddress(address, newAddress);
    }

}
