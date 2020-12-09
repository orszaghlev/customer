package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.exception.UnknownCityException;
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
    public void testCreateAddress() throws UnknownCityException {
        doReturn(CityEntity.builder().id(1).build())
                .when(dao).queryCity(anyInt());
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
                1,
                "address1",
                "address2",
                "district",
                1,
                "1234",
                "061234567"
        );
    }

}
