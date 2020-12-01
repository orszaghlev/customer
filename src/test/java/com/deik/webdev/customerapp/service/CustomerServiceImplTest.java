package com.deik.webdev.customerapp.service;

import com.deik.webdev.customerapp.dao.CustomerDao;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;
    @Mock
    private CustomerDao dao;

    @Test
    public void testRecordCustomer() throws UnknownStaffException, UnknownCountryException {
        Customer customer = getCustomer();
        service.recordCustomer(customer);

        verify(dao, times(1)).createCustomer(customer);
    }

    @Test
    void testRecordCustomerWithUnknownStaff() throws UnknownStaffException, UnknownCountryException {
        doThrow(UnknownStaffException.class).when(dao).createCustomer(any());

        assertThrows(UnknownStaffException.class, ()->{
            service.recordCustomer(getCustomer());
        });
    }

    @Test
    void testRecordCustomerWithUnknownCountry() throws UnknownStaffException, UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createCustomer(any());

        assertThrows(UnknownCountryException.class, ()->{
            service.recordCustomer(getCustomer());
        });
    }

    @Test
    void testReadAllCustomers() {
        when(dao.readAll()).thenReturn(getDefaultCustomers());
        Collection<Customer> actual = service.getAllCustomer();

        assertThat(getDefaultCustomers(), is(actual));
    }

    @Test
    void testDeleteCustomer() throws UnknownCustomerException {
        Customer customer = getCustomer();
        service.deleteCustomer(customer);

        verify(dao, times(1)).deleteCustomer(customer);
    }

    private Customer getCustomer() {
        return new Customer(
                "store",
                "staff",
                "staffAddress",
                "staffCity",
                "staffCountry",
                "firstName",
                "lastName",
                "email",
                "address",
                "city",
                "country"
        );
    }

    private Collection<Customer> getDefaultCustomers() {
        return Arrays.asList(
                new Customer(
                        "store",
                        "staff",
                        "staffAddress",
                        "staffCity",
                        "staffCountry",
                        "firstName",
                        "lastName",
                        "email",
                        "address",
                        "city",
                        "country"
                ),
                new Customer(
                        "store1",
                        "staff1",
                        "staffAddress1",
                        "staffCity1",
                        "staffCountry1",
                        "firstName1",
                        "lastName1",
                        "email1",
                        "address1",
                        "city1",
                        "country1"
                ),
                new Customer(
                        "store2",
                        "staff2",
                        "staffAddress2",
                        "staffCity2",
                        "staffCountry2",
                        "firstName2",
                        "lastName2",
                        "email2",
                        "address2",
                        "city2",
                        "country2"
                ));
    }

}
