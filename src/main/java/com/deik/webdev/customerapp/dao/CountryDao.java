package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;

import java.util.Collection;

public interface CountryDao {

    void createCountry(Country country);
    Collection<Country> readAll();
    Country readCountryById(Integer id) throws UnknownCountryException, EmptyException, OutOfBoundsException;

    void deleteCountry(Country country) throws UnknownCountryException;

    void updateCountry(Country country, Country newCountry) throws UnknownCountryException;

}
