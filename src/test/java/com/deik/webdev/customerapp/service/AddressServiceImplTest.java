package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.AddressDao;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl service;
    @Mock
    private AddressDao dao;

    @Test
    public void testRecordAddress() throws UnknownCountryException {
        Address address = getAddress();
        service.recordAddress(address);

        verify(dao, times(1)).createAddress(address);
    }

    @Test
    public void testRecordAddressWithUnknownCountry() throws UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createAddress(any());

        assertThrows(UnknownCountryException.class, ()->{
            service.recordAddress(getAddress());
        });
    }

    @Test
    public void testReadAllAddresses() {
        when(dao.readAll()).thenReturn(getDefaultAddresses());
        Collection<Address> actual = service.getAllAddress();

        assertThat(getDefaultAddresses(), is(actual));
    }

    @Test
    public void testReadAddressesFromCity() throws UnknownAddressException, EmptyException {
        final String city = "city";
        Collection<Address> actual = service.getAddressesByCity(city);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadAddressesFromDistrict() throws UnknownAddressException, EmptyException {
        final String district = "district";
        Collection<Address> actual = service.getAddressesByDistrict(district);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadAddressesFromPostalCode() throws UnknownAddressException, EmptyException {
        final String postalCode = "1234";
        Collection<Address> actual = service.getAddressesByPostalCode(postalCode);

        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    public void testReadAddressById() throws UnknownAddressException, OutOfBoundsException, EmptyException {
        final Integer id = 1;
        service.getAddressById(id);

        verify(dao, times(1)).readAddressById(id);
    }

    @Test
    public void testDeleteAddress() throws UnknownAddressException {
        Address address = getAddress();
        service.deleteAddress(address);

        verify(dao, times(1)).deleteAddress(address);
    }

    @Test
    public void testUpdateAddress() throws UnknownCountryException, UnknownAddressException {
        Address address = getAddress();
        Address newAddress = getNewAddress();
        service.updateAddress(address, newAddress);

        verify(dao, times(1)).updateAddress(address, newAddress);
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

    private Address getNewAddress() {
        return new Address(
                "newAddress1",
                "newAddress2",
                "newDistrict",
                "newUnknownCity",
                "newAlgeria_1234",
                "2345",
                "062345678"
        );
    }

    private Collection<Address> getDefaultAddresses() {
        return Arrays.asList(
                new Address(
                        "address1",
                        "address2",
                        "district",
                        "Atlantis",
                        "Greece",
                        "1234",
                        "061234567"
                ),
                new Address(
                        "address10",
                        "address20",
                        "district",
                        "Atlantis",
                        "Greece",
                        "2345",
                        "062345678"
                ),
                new Address(
                        "address1",
                        "address2",
                        "district",
                        "Debrecen",
                        "Hungary",
                        "3456",
                        "063456789"
                ));
    }

}
