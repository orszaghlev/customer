package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StoreDto;
import com.deik.webdev.customerapp.dto.StoreUpdateRequestDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.exception.UnknownStaffException;
import com.deik.webdev.customerapp.exception.UnknownStoreException;
import com.deik.webdev.customerapp.model.Store;
import com.deik.webdev.customerapp.service.StoreService;
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
public class StoreController {

    private final StoreService service;

    @GetMapping("/store")
    public Collection<StoreDto> listStores() {
        return service.getAllStore()
                .stream()
                .map(model -> StoreDto.builder()
                        .id(model.getId())
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
                    requestDto.getId(),
                    requestDto.getStaff(),
                    requestDto.getStaffAddress(),
                    requestDto.getStaffCity(),
                    requestDto.getStaffCountry(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (DataAccessException | UnknownStaffException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/store")
    public void deleteStore(@RequestBody StoreDto requestDto){
        try {
            service.deleteStore(new Store(
                    requestDto.getId(),
                    requestDto.getStaff(),
                    requestDto.getStaffAddress(),
                    requestDto.getStaffCity(),
                    requestDto.getStaffCountry(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (DataAccessException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/store")
    public void updateStore(@RequestBody StoreUpdateRequestDto requestDto) {
        try {
            service.updateStore(new Store(
                    requestDto.getId(),
                    requestDto.getStaff(),
                    requestDto.getStaffAddress(),
                    requestDto.getStaffCity(),
                    requestDto.getStaffCountry(),
                    requestDto.getAddress(),
                    requestDto.getCity(),
                    requestDto.getCountry()),
                    new Store(
                    requestDto.getNewId(),
                    requestDto.getNewStaff(),
                    requestDto.getNewStaffAddress(),
                    requestDto.getNewStaffCity(),
                    requestDto.getNewStaffCountry(),
                    requestDto.getNewAddress(),
                    requestDto.getNewCity(),
                    requestDto.getNewCountry())
            );
        } catch (DataAccessException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
