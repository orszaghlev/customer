package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDaoImplTest {

    @Spy
    @InjectMocks
    private AddressDaoImpl dao;
    @Mock
    private AddressRepository addressRepository;

    @Test
    public void testCreateAddress() throws UnknownCountryException {
        doReturn(CityEntity.builder().city("Debrecen").build())
                .when(dao).queryCity(any(),any());
        dao.createAddress(getAddress());

        verify(addressRepository, times(1)).save(any());
    }

    @Test
    public void testReadAllAddresses() {
        dao.readAll();

        verify(addressRepository, times(1)).findAll();
    }

    private Address getAddress() {
        return new Address(
                "address1",
                "address2",
                "district",
                "UnknownCity",
                "Algeria_1234",
                "1234",
                "061234567"
        );
    }

}
