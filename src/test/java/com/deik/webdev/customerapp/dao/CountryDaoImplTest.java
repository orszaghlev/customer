package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.model.Country;
import com.deik.webdev.customerapp.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private Country getCountry() {
        return new Country(
                "name"
        );
    }

}
