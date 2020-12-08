package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CityDto;
import com.deik.webdev.customerapp.dto.CityUpdateRequestDto;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import com.deik.webdev.customerapp.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

    @InjectMocks
    private CityController cityController;
    @Mock
    private CityService cityService;

    @Test
    public void testListCities() {
        when(cityService.getAllCity()).thenReturn(getCities());
        cityController.listCities();

        verify(cityService, times(1)).getAllCity();
    }

    @Test
    public void testListCitiesByCountry() throws UnknownCityException, EmptyException {
        when(cityService.getCitiesByCountry(any())).thenReturn(getCities());
        cityController.listCitiesByCountry(anyString());

        verify(cityService, times(1)).getCitiesByCountry(anyString());
    }

    @Test
    public void testRecordCity() throws UnknownCountryException {
        cityController.recordCity(getCityDto());

        verify(cityService, times(1)).recordCity(getCity());
    }

    @Test
    public void testDeleteCity() throws UnknownCityException {
        cityController.deleteCity(getCityDto());

        verify(cityService, times(1)).deleteCity(any());
    }

    @Test
    public void testUpdateCity() throws UnknownCityException, UnknownCountryException {
        cityController.updateCity(getCityUpdateRequestDto());

        verify(cityService, times(1)).updateCity(any(), any());
    }

    private City getCity() {
        return new City(
                "city",
                "country"
        );
    }

    private CityDto getCityDto() {
        return new CityDto(
                "city",
                "country"
        );
    }

    private CityUpdateRequestDto getCityUpdateRequestDto() {
        return new CityUpdateRequestDto(
                "city",
                "country"
        );
    }

    private Collection<City> getCities() {
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
