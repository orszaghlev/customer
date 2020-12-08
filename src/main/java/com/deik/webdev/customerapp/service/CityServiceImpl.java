package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CityDao;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Override
    public Collection<City> getAllCity() {
        return cityDao.readAll();
    }

    @Override
    public Collection<City> getCitiesByCountry(String country) throws UnknownCityException, EmptyException {
        return cityDao.readCitiesByCountry(country);
    }

    @Override
    public void recordCity(City city) throws UnknownCountryException {
        cityDao.createCity(city);
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException {
        cityDao.deleteCity(city);
    }

    @Override
    public void updateCity(City city, City newCity) throws UnknownCountryException, UnknownCityException {
        cityDao.updateCity(city, newCity);
    }

}
