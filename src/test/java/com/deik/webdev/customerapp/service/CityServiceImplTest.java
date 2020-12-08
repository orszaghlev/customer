package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CityDao;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @InjectMocks
    private CityServiceImpl service;
    @Mock
    private CityDao dao;

    @Test
    public void testRecordCity() throws UnknownCountryException {
        City city = getCity();
        service.recordCity(city);

        verify(dao, times(1)).createCity(city);
    }

    @Test
    public void testRecordCityWithUnknownCountry() throws UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createCity(any());

        assertThrows(UnknownCountryException.class, ()->{
            service.recordCity(getCity());
        });
    }

    @Test
    public void testReadAllCities() {
        when(dao.readAll()).thenReturn(getDefaultCities());
        Collection<City> actual = service.getAllCity();

        assertThat(getDefaultCities(), is(actual));
    }

    @Test
    public void testReadCitiesByCountry() throws UnknownCityException, EmptyException {
        final String country = "country";
        Collection<City> actual = service.getCitiesByCountry(country);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testDeleteCity() throws UnknownCityException {
        City city = getCity();
        service.deleteCity(city);

        verify(dao, times(1)).deleteCity(city);
    }

    @Test
    public void testUpdateCity() throws UnknownCountryException, UnknownCityException {
        City city = getCity();
        City newCity = getNewCity();
        service.updateCity(city, newCity);

        verify(dao, times(1)).updateCity(city, newCity);
    }

    private City getCity() {
        return new City(
                "city",
                "country"
        );
    }

    private City getNewCity() {
        return new City(
                "newCity",
                "newCountry"
        );
    }

    private Collection<City> getDefaultCities() {
        return Arrays.asList(
                new City(
                        "city",
                        "country"
                ),
                new City(
                        "city1",
                        "country1"
                ),
                new City(
                        "city2",
                        "country2"
                ));
    }

}
