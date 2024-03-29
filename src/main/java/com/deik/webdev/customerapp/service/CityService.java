package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;

import java.util.Collection;

public interface CityService {

    Collection<City> getAllCity();
    Collection<City> getCitiesByCountry(String country) throws UnknownCityException, EmptyException;
    City getCityById(Integer id) throws UnknownCityException, EmptyException, OutOfBoundsException;

    void recordCity(City city) throws UnknownCountryException;
    void deleteCity(City city) throws UnknownCityException;
    void updateCity(City city, City newCity) throws UnknownCountryException, UnknownCityException;

}
