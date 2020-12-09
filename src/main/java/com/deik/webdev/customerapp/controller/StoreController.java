package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StoreDto;
import com.deik.webdev.customerapp.dto.StoreUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;
import com.deik.webdev.customerapp.service.StoreService;
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
public class StoreController {

    private final StoreService service;

    @GetMapping("/store")
    public Collection<StoreDto> listStores() {
        return service.getAllStore()
                .stream()
                .map(model -> StoreDto.builder()
                        .id(model.getId())
                        .staffId(model.getStaffId())
                        .addressId(model.getAddressId())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/store/{staffId}")
    public Collection<StoreDto> listStoresByStaffId(Integer staffId) {
        try {
            return service.getStoresByStaffId(staffId)
                    .stream()
                    .map(model -> StoreDto.builder()
                            .id(model.getId())
                            .staffId(model.getStaffId())
                            .addressId(model.getAddressId())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/store/{id}")
    public StoreDto listStoreById(Integer id) {
        try {
            Store store = service.getStoreById(id);
            return new StoreDto(
                    store.getId(),
                    store.getStaffId(),
                    store.getAddressId());
        } catch (OutOfBoundsException | EmptyException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/store")
    public void recordStore(@RequestBody StoreDto requestDto) {
        try {
            service.recordStore(new Store(
                    requestDto.getId(),
                    requestDto.getStaffId(),
                    requestDto.getAddressId()
            ));
        } catch (OutOfBoundsException | UnknownStaffException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/store")
    public void deleteStore(@RequestBody StoreDto requestDto) {
        try {
            service.deleteStore(new Store(
                    requestDto.getId(),
                    requestDto.getStaffId(),
                    requestDto.getAddressId()
            ));
        } catch (UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/store")
    public void updateStore(@RequestBody StoreUpdateRequestDto requestDto) {
        try {
            service.updateStore(new Store(
                    requestDto.getId(),
                    requestDto.getStaffId(),
                    requestDto.getAddressId()),
                    new Store(
                    requestDto.getId(),
                    requestDto.getNewStaffId(),
                    requestDto.getNewAddressId())
            );
        } catch (OutOfBoundsException | UnknownStaffException | UnknownAddressException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
