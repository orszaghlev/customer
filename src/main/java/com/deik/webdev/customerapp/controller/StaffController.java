package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StaffDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.service.StaffService;
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
public class StaffController {

    private final StaffService service;

    @GetMapping("/staff")
    public Collection<StaffDto> listStaffs() {
        return service.getAllStaff()
                .stream()
                .map(model -> StaffDto.builder()
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .address(model.getAddress())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .email(model.getEmail())
                        .store(model.getStore())
                        .storeAddress(model.getStoreAddress())
                        .storeCity(model.getStoreCity())
                        .storeCountry(model.getStoreCountry())
                        .username(model.getUsername())
                        .password(model.getPassword())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/staff")
    public void record(@RequestBody StaffDto requestDto) {
        try {
            service.recordStaff(new Staff(
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry(),
                    requestDto.getEmail(),
                    requestDto.getStore(),
                    requestDto.getStoreAddress(),
                    requestDto.getStoreCity(),
                    requestDto.getStoreCountry(),
                    requestDto.getUsername(),
                    requestDto.getPassword()
            ));
        } catch (NumberFormatException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
