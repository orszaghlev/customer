package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.AddressDto;
import com.deik.webdev.customerapp.dto.AddressUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.service.AddressService;
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
public class AddressController {

    private final AddressService service;

    @GetMapping("/address")
    public Collection<AddressDto> listAddresses() {
        return service.getAllAddress()
                .stream()
                .map(model -> AddressDto.builder()
                        .id(model.getId())
                        .address(model.getAddress())
                        .address2(model.getAddress2())
                        .district(model.getDistrict())
                        .cityId(model.getCityId())
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
                    .map(model -> AddressDto.builder()
                            .id(model.getId())
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .cityId(model.getCityId())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/address/{district}")
    public Collection<AddressDto> listAddressesByDistrict(String district) {
        try {
            return service.getAddressesByDistrict(district)
                    .stream()
                    .map(model -> AddressDto.builder()
                            .id(model.getId())
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .cityId(model.getCityId())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/address/{postalCode}")
    public Collection<AddressDto> listAddressesByPostalCode(String postalCode) {
        try {
            return service.getAddressesByPostalCode(postalCode)
                    .stream()
                    .map(model -> AddressDto.builder()
                            .id(model.getId())
                            .address(model.getAddress())
                            .address2(model.getAddress2())
                            .district(model.getDistrict())
                            .cityId(model.getCityId())
                            .postalCode(model.getPostalCode())
                            .phone(model.getPhone())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/address/{id}")
    public AddressDto listAddressById(Integer id) {
        try {
            Address address = service.getAddressById(id);
            return new AddressDto(
                    address.getId(),
                    address.getAddress(),
                    address.getAddress2(),
                    address.getDistrict(),
                    address.getCityId(),
                    address.getPostalCode(),
                    address.getPhone());
        } catch (OutOfBoundsException | EmptyException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/address")
    public void recordAddress(@RequestBody AddressDto requestDto) {
        try {
            service.recordAddress(new Address(
                    requestDto.getId(),
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCityId(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()
            ));
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/address")
    public void deleteAddress(@RequestBody AddressDto requestDto) {
        try {
            service.deleteAddress(new Address(
                    requestDto.getId(),
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCityId(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()
            ));
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/address")
    public void updateAddress(@RequestBody AddressUpdateRequestDto requestDto) {
        try {
            service.updateAddress(new Address(
                    requestDto.getId(),
                    requestDto.getAddress(),
                    requestDto.getAddress2(),
                    requestDto.getDistrict(),
                    requestDto.getCityId(),
                    requestDto.getPostalCode(),
                    requestDto.getPhone()),
                    new Address(
                    requestDto.getId(),
                    requestDto.getNewAddress(),
                    requestDto.getNewAddress2(),
                    requestDto.getNewDistrict(),
                    requestDto.getNewCityId(),
                    requestDto.getNewPostalCode(),
                    requestDto.getNewPhone())
            );
        } catch (UnknownCityException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
