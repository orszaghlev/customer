package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CityDao;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
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
    public void testReadCityById() throws UnknownCityException, OutOfBoundsException, EmptyException {
        final Integer id = 1;
        service.getCityById(id);

        verify(dao, times(1)).readCityById(id);
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
                1,
                "city",
                1
        );
    }

    private City getNewCity() {
        return new City(
                1,
                "newCity",
                1
        );
    }

    private Collection<City> getDefaultCities() {
        return Arrays.asList(
                new City(
                        1,
                        "city",
                        1
                ),
                new City(
                        2,
                        "city1",
                        2
                ),
                new City(
                        3,
                        "city2",
                        3
                ));
    }

}
