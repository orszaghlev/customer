package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CountryEntity;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import com.deik.webdev.customerapp.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityDaoImplTest {

    @Spy
    @InjectMocks
    private CityDaoImpl dao;
    @Mock
    private CityRepository cityRepository;

    @Test
    void testCreateCity() throws UnknownCountryException {
        doReturn(CountryEntity.builder().country("Hungary").build())
                .when(dao).queryCountry(any());
        dao.createCity(getCity());

        verify(cityRepository, times(1)).save(any());
    }

    @Test
    void testReadAllCities() {
        dao.readAll();

        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void deleteCity() throws UnknownCityException {
        doThrow(UnknownCityException.class).when(dao).deleteCity(any());

        assertThrows(UnknownCityException.class, ()->{
            dao.deleteCity(getCity());
        });
    }

    @Test
    public void updateCity() throws UnknownCountryException, UnknownCityException {
        doThrow(UnknownCityException.class).when(dao).updateCity(any(), any());

        assertThrows(UnknownCityException.class, ()->{
            dao.updateCity(getCity(), getNewCity());
        });
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

}
