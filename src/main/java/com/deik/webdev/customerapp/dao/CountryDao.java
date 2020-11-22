package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;

import java.util.Collection;

public interface CountryDao {

    void createCountry(Country country);
    Collection<Country> readAll();

    void deleteCountry(Country country) throws UnknownCountryException;

}
