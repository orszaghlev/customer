package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.AddressDto;
import com.deik.webdev.customerapp.dto.AddressRecordRequestDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
public class AddressController {

    private final AddressService service;

    @GetMapping("/address")
    public Collection<AddressDto> listAddresses() {
        return service.getAllAddress()
                .stream()
                .map(model -> AddressDto.builder()
                        .address(model.getAddress())
                        .address2(model.getAddress2())
                        .district(model.getDistrict())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
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

}
