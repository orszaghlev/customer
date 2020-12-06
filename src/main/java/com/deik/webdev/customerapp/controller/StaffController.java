package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StaffDto;
import com.deik.webdev.customerapp.dto.StaffUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.service.StaffService;
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
public class StaffController {

    private final StaffService service;

    @GetMapping("/staff")
    public Collection<StaffDto> listStaffs() {
        return service.getAllStaff()
                .stream()
                .map(model -> StaffDto.builder()
                        .id(model.getId())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .addressId(model.getAddressId())
                        .email(model.getEmail())
                        .storeId(model.getStoreId())
                        .active(model.getActive())
                        .username(model.getUsername())
                        .password(model.getPassword())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/staff/{username}")
    public Collection<StaffDto> listStaffByUsername(String username) {
        try {
            return service.getStaffByUsername(username)
                .stream()
                .map(model -> StaffDto.builder()
                        .id(model.getId())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .addressId(model.getAddressId())
                        .email(model.getEmail())
                        .storeId(model.getStoreId())
                        .active(model.getActive())
                        .username(model.getUsername())
                        .password(model.getPassword())
                        .build())
                .collect(Collectors.toList());
        } catch (UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/staff/{email}")
    public Collection<StaffDto> listStaffByEmail(String email) {
        try {
            return service.getStaffByEmail(email)
                    .stream()
                    .map(model -> StaffDto.builder()
                            .id(model.getId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .addressId(model.getAddressId())
                            .email(model.getEmail())
                            .storeId(model.getStoreId())
                            .active(model.getActive())
                            .username(model.getUsername())
                            .password(model.getPassword())
                            .build())
                    .collect(Collectors.toList());
        } catch (UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/staff/{storeId}")
    public Collection<StaffDto> listStaffByStoreId(Integer storeId) {
        try {
            return service.getStaffByStoreId(storeId)
                    .stream()
                    .map(model -> StaffDto.builder()
                            .id(model.getId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .addressId(model.getAddressId())
                            .email(model.getEmail())
                            .storeId(model.getStoreId())
                            .active(model.getActive())
                            .username(model.getUsername())
                            .password(model.getPassword())
                            .build())
                    .collect(Collectors.toList());
        } catch (OutOfBoundsException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/staff")
    public void recordStaff(@RequestBody StaffDto requestDto) {
        try {
            service.recordStaff(new Staff(
                    requestDto.getId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getAddressId(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()
            ));
        } catch (DataAccessException | NumberFormatException | OutOfBoundsException | UnknownStoreException | UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/staff")
    public void deleteStaff(@RequestBody StaffDto requestDto){
        try {
            service.deleteStaff(new Staff(
                    requestDto.getId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getAddressId(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()
            ));
        } catch (DataAccessException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/staff")
    public void updateStaff(@RequestBody StaffUpdateRequestDto requestDto) {
        try {
            service.updateStaff(new Staff(
                    requestDto.getId(),
                    requestDto.getFirstName(),
                    requestDto.getLastName(),
                    requestDto.getAddressId(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()),
                    new Staff(
                    requestDto.getNewId(),
                    requestDto.getNewFirstName(),
                    requestDto.getNewLastName(),
                    requestDto.getNewAddressId(),
                    requestDto.getNewEmail(),
                    requestDto.getNewStoreId(),
                    requestDto.getNewActive(),
                    requestDto.getNewUsername(),
                    requestDto.getNewPassword())
            );
        } catch (DataAccessException | NumberFormatException | OutOfBoundsException | UnknownStoreException | UnknownAddressException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
