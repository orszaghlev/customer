package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;

import java.util.Collection;

public interface CountryService {

    Collection<Country> getAllCountry();

    void createCountry(Country country);
    void deleteCountry(Country country) throws UnknownCountryException;

}
