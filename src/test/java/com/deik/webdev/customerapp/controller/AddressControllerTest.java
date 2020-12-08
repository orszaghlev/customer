package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.AddressDto;
import com.deik.webdev.customerapp.dto.AddressRecordRequestDto;
import com.deik.webdev.customerapp.dto.AddressUpdateRequestDto;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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
    public void testRecordAddress() throws UnknownCountryException {
        addressController.recordAddress(getAddressRecordRequestDto());

        verify(addressService, times(1)).recordAddress(getAddress());
    }

    @Test
    public void testDeleteAddress() throws UnknownAddressException {
        addressController.deleteAddress(getAddressRecordRequestDto());

        verify(addressService, times(1)).deleteAddress(any());
    }

    @Test
    public void testUpdateAddress() throws UnknownAddressException, UnknownCountryException {
        addressController.updateAddress(getAddressUpdateRequestDto());

        verify(addressService, times(1)).updateAddress(any(), any());
    }

    private Address getAddress() {
        return new Address(
                null,
                null,
                null,
                null,
                null,
                "1234",
                "061234567"
        );
    }

    private AddressDto getAddressDto() {
        return new AddressDto(
                "address1",
                "address2",
                "district",
                "UnknownCity",
                "Algeria_1234"
        );
    }

    private AddressRecordRequestDto getAddressRecordRequestDto() {
        return new AddressRecordRequestDto(
                "061234567",
                "1234"
        );
    }

    private AddressUpdateRequestDto getAddressUpdateRequestDto() {
        return new AddressUpdateRequestDto(
                "address1",
                "address2",
                "district",
                "UnknownCity",
                "Algeria_1234",
                "1234",
                "061234567"
        );
    }

    private Collection<Address> getAddresses() {
        return Arrays.asList(
                new Address(
                        "address1",
                        "address2",
                        "district",
                        "UnknownCity",
                        "Algeria_1234",
                        "1234",
                        "061234567"
                ),
                new Address(
                        "address3",
                        "address4",
                        "district",
                        "UnknownCity",
                        "Algeria_1234",
                        "1234",
                        "061234567"
                ),
                new Address(
                        "address5",
                        "address6",
                        "district",
                        "UnknownCity",
                        "Algeria_1234",
                        "1234",
                        "061234567"
                )
        );
    }

}

