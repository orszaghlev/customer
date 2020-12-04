package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
import com.deik.webdev.customerapp.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryDaoImplTest {

    @Spy
    @InjectMocks
    private CountryDaoImpl dao;
    @Mock
    private CountryRepository countryRepository;

    @Test
    void testCreateCountry() {
        dao.createCountry(getCountry());

        verify(countryRepository, times(1)).save(any());
    }

    @Test
    public void deleteCountry() throws UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).deleteCountry(any());

        assertThrows(UnknownCountryException.class, ()->{
            dao.deleteCountry(getCountry());
        });
    }

    @Test
    public void updateCountry() throws UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).updateCountry(any(), any());

        assertThrows(UnknownCountryException.class, ()->{
            dao.updateCountry(getCountry(), getNewCountry());
        });
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

}
