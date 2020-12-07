package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.AddressDto;
import com.deik.webdev.customerapp.dto.AddressRecordRequestDto;
import com.deik.webdev.customerapp.dto.AddressUpdateRequestDto;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.service.AddressService;
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
public class AddressController {

    private final AddressService service;

    @GetMapping("/address")
    public Collection<AddressDto> listAddresses() {
        return service.getAllAddress()
                .stream()
                .map(model -> AddressRecordRequestDto.builder()
                        .address(model.getAddress())
                        .address2(model.getAddress2())
                        .district(model.getDistrict())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .postalCode(model.getPostalCode())
                        .phone(model.getPhone())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/address/{city}")
    public Collection<AddressDto> listAddressesByCity(String city) {
        try {
            return service.getAddressesByCity(city)
                    .stream()
                    .map(model -> AddressRecordRequestDto.builder()
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .city(model.getCity())
                            .country(model.getCountry())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/address/{district}")
    public Collection<AddressDto> listAddressesByDistrict(String district) {
        try {
            return service.getAddressesByDistrict(district)
                    .stream()
                    .map(model -> AddressRecordRequestDto.builder()
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .city(model.getCity())
                            .country(model.getCountry())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/address/{postalCode}")
    public Collection<AddressDto> listAddressesByPostalCode(String postalCode) {
        try {
            return service.getAddressesByPostalCode(postalCode)
                    .stream()
                    .map(model -> AddressRecordRequestDto.builder()
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .city(model.getCity())
                            .country(model.getCountry())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/address")
    public void recordAddress(@RequestBody AddressRecordRequestDto requestDto) {
        try {
            service.recordAddress(new Address(
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()
            ));
        } catch (DataAccessException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/address")
    public void deleteAddress(@RequestBody AddressRecordRequestDto requestDto) {
        try {
            service.deleteAddress(new Address(
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()
            ));
        } catch (DataAccessException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/address")
    public void updateAddress(@RequestBody AddressUpdateRequestDto requestDto) {
        try {
            service.updateAddress(new Address(
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()),
                    new Address(
                    requestDto.getNewAddress(),
                    requestDto.getNewAddress2(),
                    requestDto.getNewDistrict(),
                    requestDto.getNewCity(),
                    requestDto.getNewCountry(),
                    requestDto.getNewPostalCode(),
                    requestDto.getNewPhone())
            );
        } catch (DataAccessException | UnknownCountryException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
