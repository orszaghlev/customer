package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StoreDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Store;
import com.deik.webdev.customerapp.service.StoreService;
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
public class StoreController {

    private final StoreService service;

    @GetMapping("/store")
    public Collection<StoreDto> listStores() {
        return service.getAllStore()
                .stream()
                .map(model -> StoreDto.builder()
                        .staff(model.getStaff())
                        .staffAddress(model.getStaffAddress())
                        .staffCity(model.getStaffCity())
                        .staffCountry(model.getStaffCountry())
                        .address(model.getAddress())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/store")
    public void recordStore(@RequestBody StoreDto requestDto) {
        try {
            service.recordStore(new Store(
                    requestDto.getStaff(),
                    requestDto.getStaffAddress(),
                    requestDto.getStaffCity(),
                    requestDto.getStaffCountry(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
