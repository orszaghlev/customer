package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CustomerDto;
import com.deik.webdev.customerapp.dto.CustomerUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Customer;
import com.deik.webdev.customerapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                        .id(model.getId())
                        .storeId(model.getStoreId())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .email(model.getEmail())
                        .addressId(model.getAddressId())
                        .active(model.getActive())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{firstName}/{lastName}")
    public Collection<CustomerDto> listCustomersByName(String firstName, String lastName) {
        try {
            return service.getCustomersByName(firstName, lastName)
                    .stream()
                    .map(model -> CustomerDto.builder()
                            .id(model.getId())
                            .storeId(model.getStoreId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .email(model.getEmail())
                            .addressId(model.getAddressId())
                            .active(model.getActive())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customer/{email}")
    public Collection<CustomerDto> listCustomersByEmail(String email) {
        try {
            return service.getCustomersByEmail(email)
                    .stream()
                    .map(model -> CustomerDto.builder()
                            .id(model.getId())
                            .storeId(model.getStoreId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .email(model.getEmail())
                            .addressId(model.getAddressId())
                            .active(model.getActive())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customer/{storeId}")
    public Collection<CustomerDto> listCustomersByStoreId(Integer storeId) {
        try {
            return service.getCustomersByStoreId(storeId)
                    .stream()
                    .map(model -> CustomerDto.builder()
                            .id(model.getId())
                            .storeId(model.getStoreId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .email(model.getEmail())
                            .addressId(model.getAddressId())
                            .active(model.getActive())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customer/{active}")
    public Collection<CustomerDto> listActiveCustomers(Integer active) {
        try {
            return service.getActiveCustomers(active)
                    .stream()
                    .map(model -> CustomerDto.builder()
                            .id(model.getId())
                            .storeId(model.getStoreId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .email(model.getEmail())
                            .addressId(model.getAddressId())
                            .active(model.getActive())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | OutOfBoundsException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customer/{id}")
    public CustomerDto listCustomerById(Integer id) {
        try {
            Customer customer = service.getCustomerById(id);
            return new CustomerDto(
                    customer.getId(),
                    customer.getStoreId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getAddressId(),
                    customer.getActive());
        } catch (OutOfBoundsException | EmptyException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/customer")
    public void recordCustomer(@RequestBody CustomerDto requestDto) {
        try {
            service.recordCustomer(new Customer(
                    requestDto.getId(),
                    requestDto.getStoreId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddressId(),
                    requestDto.getActive()
            ));
        } catch (OutOfBoundsException | UnknownStoreException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestBody CustomerDto requestDto){
        try {
            service.deleteCustomer(new Customer(
                    requestDto.getId(),
                    requestDto.getStoreId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddressId(),
                    requestDto.getActive()
            ));
        } catch (UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/customer")
    public void updateCustomer(@RequestBody CustomerUpdateRequestDto requestDto) {
        try {
            service.updateCustomer(new Customer(
                    requestDto.getId(),
                    requestDto.getStoreId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getEmail(),
                    requestDto.getAddressId(),
                    requestDto.getActive()),
                    new Customer(
                    requestDto.getId(),
                    requestDto.getNewStoreId(),
                    requestDto.getNewFirstName(),
                    requestDto.getNewLastName(),
                    requestDto.getNewEmail(),
                    requestDto.getNewAddressId(),
                    requestDto.getNewActive())
            );
        } catch (OutOfBoundsException | UnknownStoreException | UnknownAddressException | UnknownCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
