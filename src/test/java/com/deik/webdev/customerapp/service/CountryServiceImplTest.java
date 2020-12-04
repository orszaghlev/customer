package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CountryDao;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl service;
    @Mock
    private CountryDao dao;

    @Test
    public void testRecordCountry() {
        Country country = getCountry();
        service.recordCountry(country);

        verify(dao, times(1)).createCountry(country);
    }

    @Test
    void testReadAllCountries() {
        when(dao.readAll()).thenReturn(getDefaultCountries());
        Collection<Country> actual = service.getAllCountry();

        assertThat(getDefaultCountries(), is(actual));
    }

    @Test
    void testDeleteCountry() throws UnknownCountryException {
        Country country = getCountry();
        service.deleteCountry(country);

        verify(dao, times(1)).deleteCountry(country);
    }

    @Test
    void testUpdateCountry() throws UnknownCountryException {
        Country country = getCountry();
        Country newCountry = getNewCountry();
        service.updateCountry(country, newCountry);

        verify(dao, times(1)).updateCountry(country, newCountry);
    }

    private Country getCountry() {
        return new Country(
                "country"
        );
    }

    private Country getNewCountry() {
        return new Country(
                "newCountry"
        );
    }

    private Collection<Country> getDefaultCountries() {
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
