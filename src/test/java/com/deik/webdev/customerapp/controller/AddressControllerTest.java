package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.AddressDto;
import com.deik.webdev.customerapp.dto.AddressUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;
    @Mock
    private AddressService addressService;

    @Test
    public void testListAddresses() {
        when(addressService.getAllAddress()).thenReturn(getAddresses());
        addressController.listAddresses();

        verify(addressService, times(1)).getAllAddress();
    }

    @Test
    public void testListAddressesByCity() throws UnknownAddressException, EmptyException {
        when(addressService.getAddressesByCity(any())).thenReturn(getAddresses());
        addressController.listAddressesByCity(anyString());

        verify(addressService, times(1)).getAddressesByCity(anyString());
    }

    @Test
    public void testListAddressesByDistrict() throws UnknownAddressException, EmptyException {
        when(addressService.getAddressesByDistrict(any())).thenReturn(getAddresses());
        addressController.listAddressesByDistrict(anyString());

        verify(addressService, times(1)).getAddressesByDistrict(anyString());
    }

    @Test
    public void testListAddressesByPostalCode() throws UnknownAddressException, EmptyException {
        when(addressService.getAddressesByPostalCode(any())).thenReturn(getAddresses());
        addressController.listAddressesByPostalCode(anyString());

        verify(addressService, times(1)).getAddressesByPostalCode(anyString());
    }

    @Test
    public void testListAddressById() throws UnknownAddressException, OutOfBoundsException, EmptyException {
        when(addressService.getAddressById(any())).thenReturn(getAddress());
        addressController.listAddressById(anyInt());

        verify(addressService, times(1)).getAddressById(any());
    }

    @Test
    public void testRecordAddress() throws UnknownCityException {
        addressController.recordAddress(getAddressDto());

        verify(addressService, times(1)).recordAddress(getAddress());
    }

    @Test
    public void testDeleteAddress() throws UnknownAddressException {
        addressController.deleteAddress(getAddressDto());

        verify(addressService, times(1)).deleteAddress(any());
    }

    @Test
    public void testUpdateAddress() throws UnknownAddressException, UnknownCityException {
        addressController.updateAddress(getAddressUpdateRequestDto());

        verify(addressService, times(1)).updateAddress(any(), any());
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

    private AddressDto getAddressDto() {
        return new AddressDto(
                1,
                "address1",
                "address2",
                "district",
                1,
                "1234",
                "061234567"
        );
    }

    private AddressUpdateRequestDto getAddressUpdateRequestDto() {
        return new AddressUpdateRequestDto(
                "address1",
                "address2",
                "district",
                1,
                "1234",
                "061234567"
        );
    }

    private Collection<Address> getAddresses() {
        return Arrays.asList(
                new Address(
                        1,
                        "address1",
                        "address2",
                        "district",
                        1,
                        "1234",
                        "061234567"
                ),
                new Address(
                        2,
                        "address3",
                        "address4",
                        "district",
                        2,
                        "1234",
                        "061234567"
                ),
                new Address(
                        3,
                        "address5",
                        "address6",
                        "district",
                        3,
                        "1234",
                        "061234567"
                )
        );
    }

}

