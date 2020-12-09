package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CountryDao;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;

    @Override
    public Collection<Country> getAllCountry() {
        return countryDao.readAll();
    }

    @Override
    public Country getCountryById(Integer id) throws UnknownCountryException, EmptyException, OutOfBoundsException {
        return countryDao.readCountryById(id);
    }

    @Override
    public void recordCountry(Country country) {
        countryDao.createCountry(country);
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException {
        countryDao.deleteCountry(country);
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException {
        countryDao.updateCountry(country, newCountry);
    }

}
