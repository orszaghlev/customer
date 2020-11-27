package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CountryDao;
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
    void testReadAllCountries(){
        when(dao.readAll()).thenReturn(getDefaultCountries());
        Collection<Country> actual = service.getAllCountry();

        assertThat(getDefaultCountries(), is(actual));
    }

    private Country getCountry() {
        return new Country(
                "name"
        );
    }

    private Collection<Country> getDefaultCountries(){
        return Arrays.asList(
                new Country(
                        "name"
                ),
                new Country(
                        "name1"
                ),
                new Country(
                        "name2"
                ));
    }

}
