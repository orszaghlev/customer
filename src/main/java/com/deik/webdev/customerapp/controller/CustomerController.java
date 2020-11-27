package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CustomerDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
                        .staffAddress(model.getStaffAddress())
                        .staffCity(model.getStaffCity())
                        .staffCountry(model.getStaffCountry())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .email(model.getEmail())
                        .address(model.getAddress())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/customer")
    public void recordCustomer(@RequestBody CustomerDto requestDto) {
        try {
            service.recordCustomer(new Customer(
                    requestDto.getStore(),
                    requestDto.getStaff(),
                    requestDto.getStaffAddress(),
                    requestDto.getStaffCity(),
                    requestDto.getStaffCountry(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (NumberFormatException | UnknownCountryException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
