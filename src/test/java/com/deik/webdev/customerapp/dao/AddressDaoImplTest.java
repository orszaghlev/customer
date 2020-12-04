package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.repository.AddressRepository;
import com.deik.webdev.customerapp.repository.CityRepository;
import com.deik.webdev.customerapp.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDaoImplTest {

    @Spy
    @InjectMocks
    private AddressDaoImpl dao;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;

    @Test
    void testCreateAddress() throws UnknownCountryException {
        doReturn(CityEntity.builder().city("Debrecen").build())
                .when(dao).queryCity(any(),any());
        dao.createAddress(getAddress());

        verify(addressRepository, times(1)).save(any());
    }

    @Test
    public void deleteAddress() throws UnknownAddressException {
        doThrow(UnknownAddressException.class).when(dao).deleteAddress(any());

        assertThrows(UnknownAddressException.class, ()->{
            dao.deleteAddress(getAddress());
        });
    }

    @Test
    public void updateAddress() throws UnknownCountryException, UnknownAddressException {
        doThrow(UnknownAddressException.class).when(dao).updateAddress(any(), any());

        assertThrows(UnknownAddressException.class, ()->{
            dao.updateAddress(getAddress(), getNewAddress());
        });
    }

    private Address getAddress() {
        return new Address(
                "address1",
                "address2",
                "district",
                "UnknownCity",
                "Algeria_1234",
                "postalCode",
                "phone"
        );
    }

    private Address getNewAddress() {
        return new Address(
                "newAddress1",
                "newAddress2",
                "newDistrict",
                "NewUnknownCity",
                "NewAlgeria_1234",
                "NewPostalCode",
                "newPhone"
        );
    }

}
