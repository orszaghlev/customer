package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;

import java.util.Collection;

public interface CountryService {

    Collection<Country> getAllCountry();
    Country getCountryById(Integer id) throws UnknownCountryException, EmptyException, OutOfBoundsException;

    void recordCountry(Country country);
    void deleteCountry(Country country) throws UnknownCountryException;
    void updateCountry(Country country, Country newCountry) throws UnknownCountryException;

}
