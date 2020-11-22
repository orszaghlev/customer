package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;

import java.util.Collection;

public interface CityService {

    Collection<City> getAllCity();

    void recordCity(City city) throws UnknownCountryException;
    void deleteCity(City city) throws UnknownCityException;

}
