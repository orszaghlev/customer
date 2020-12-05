package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CustomerDto;
import com.deik.webdev.customerapp.dto.CustomerUpdateRequestDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownCustomerException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/customer")
    public Collection<CustomerDto> listCustomers() {
        return service.getAllCustomer()
                .stream()
                .map(model -> CustomerDto.builder()
                        .store(model.getStore())
                        .staff(model.getStaff())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .email(model.getEmail())
                        .address(model.getAddress())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .active(model.getActive())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/customer")
    public void recordCustomer(@RequestBody CustomerDto requestDto) {
        try {
            service.recordCustomer(new Customer(
                    requestDto.getStore(),
                    requestDto.getStaff(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getActive()
            ));
        } catch (DataAccessException | NumberFormatException | UnknownCountryException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestBody CustomerDto requestDto){
        try {
            service.deleteCustomer(new Customer(
                    requestDto.getStore(),
                    requestDto.getStaff(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getActive()
            ));
        } catch (DataAccessException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/customer")
    public void updateCustomer(@RequestBody CustomerUpdateRequestDto requestDto) {
        try {
            service.updateCustomer(new Customer(
                    requestDto.getStore(),
                    requestDto.getStaff(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getActive()),
                    new Customer(
                    requestDto.getNewStore(),
                    requestDto.getNewStaff(),
                    requestDto.getNewFirstName(),
                    requestDto.getNewLastName(),
                    requestDto.getNewEmail(),
                    requestDto.getNewAddress(),
                    requestDto.getNewCity(),
                    requestDto.getNewCountry(),
                    requestDto.getNewActive())
            );
        } catch (DataAccessException | NumberFormatException | UnknownStaffException | UnknownCountryException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
