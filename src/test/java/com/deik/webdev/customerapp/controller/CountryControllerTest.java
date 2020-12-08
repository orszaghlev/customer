package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CountryDto;
import com.deik.webdev.customerapp.dto.CountryUpdateRequestDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
import com.deik.webdev.customerapp.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;
    @Mock
    private CountryService countryService;

    @Test
    public void testListCountries() {
        when(countryService.getAllCountry()).thenReturn(getCountries());
        countryController.listCountries();

        verify(countryService, times(1)).getAllCountry();
    }

    @Test
    public void testRecordCountry() {
        countryController.recordCountry(getCountryDto());

        verify(countryService, times(1)).recordCountry(getCountry());
    }

    @Test
    public void testDeleteCountry() throws UnknownCountryException {
        countryController.deleteCountry(getCountryDto());

        verify(countryService, times(1)).deleteCountry(any());
    }

    @Test
    public void testUpdateCountry() throws UnknownCountryException {
        countryController.updateCountry(getCountryUpdateRequestDto());

        verify(countryService, times(1)).updateCountry(any(), any());
    }

    private Country getCountry() {
        return new Country(
                "country"
        );
    }

    private CountryDto getCountryDto() {
        return new CountryDto(
                "country"
        );
    }

    private CountryUpdateRequestDto getCountryUpdateRequestDto() {
        return new CountryUpdateRequestDto(
                "country"
        );
    }

    private Collection<Country> getCountries() {
        return Arrays.asList(
                new Country(
                        "country"
                ),
                new Country(
                        "country1"
                ),
                new Country(
                        "country2"
                ));
    }

}
