package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;

import java.util.Collection;

public interface CityDao {

    void createCity(City city) throws UnknownCountryException;
    Collection<City> readAll();
    Collection<City> readCitiesByCountry(String country) throws UnknownCityException, EmptyException;

    void deleteCity(City city) throws UnknownCityException;

    void updateCity(City city, City newCity) throws UnknownCountryException, UnknownCityException;

}
