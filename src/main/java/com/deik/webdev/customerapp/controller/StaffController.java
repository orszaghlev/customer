package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StaffDto;
import com.deik.webdev.customerapp.dto.StaffUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.service.StaffService;
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
                        .address(model.getAddress())
                        .email(model.getEmail())
                        .storeId(model.getStoreId())
                        .active(model.getActive())
                        .username(model.getUsername())
                        .password(model.getPassword())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/staff/{firstName}/{lastName}")
    public Collection<StaffDto> listStaffByFirstNameAndLastName(String firstName, String lastName) {
        try {
            return service.getStaffByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(model -> StaffDto.builder()
                        .id(model.getId())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .address(model.getAddress())
                        .email(model.getEmail())
                        .storeId(model.getStoreId())
                        .active(model.getActive())
                        .username(model.getUsername())
                        .password(model.getPassword())
                        .build())
                .collect(Collectors.toList());
        } catch (EmptyException | UnknownStaffException e) {
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
                            .address(model.getAddress())
                            .email(model.getEmail())
                            .storeId(model.getStoreId())
                            .active(model.getActive())
                            .username(model.getUsername())
                            .password(model.getPassword())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownStaffException e) {
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
                            .address(model.getAddress())
                            .email(model.getEmail())
                            .storeId(model.getStoreId())
                            .active(model.getActive())
                            .username(model.getUsername())
                            .password(model.getPassword())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/staff/{active}")
    public Collection<StaffDto> listActiveStaff(Integer active) {
        try {
            return service.getActiveStaff(active)
                    .stream()
                    .map(model -> StaffDto.builder()
                            .id(model.getId())
                            .firstName(model.getFirstName())
                            .lastName(model.getLastName())
                            .address(model.getAddress())
                            .email(model.getEmail())
                            .storeId(model.getStoreId())
                            .active(model.getActive())
                            .username(model.getUsername())
                            .password(model.getPassword())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | OutOfBoundsException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/staff/{id}")
    public StaffDto listStaffById(Integer id) {
        try {
            Staff staff = service.getStaffById(id);
            return new StaffDto(
                    staff.getId(),
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getAddress(),
                    staff.getEmail(),
                    staff.getStoreId(),
                    staff.getActive(),
                    staff.getUsername(),
                    staff.getPassword());
        } catch (EmptyException | OutOfBoundsException | UnknownStaffException e) {
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
                    requestDto.getAddress(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()
            ));
        } catch (OutOfBoundsException | UnknownStoreException | UnknownAddressException e) {
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
                    requestDto.getAddress(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()
            ));
        } catch (UnknownStaffException e) {
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
                    requestDto.getAddress(),
                    requestDto.getEmail(),
                    requestDto.getStoreId(),
                    requestDto.getActive(),
                    requestDto.getUsername(),
                    requestDto.getPassword()),
                    new Staff(
                    requestDto.getId(),
                    requestDto.getNewFirstName(),
                    requestDto.getNewLastName(),
                    requestDto.getNewAddress(),
                    requestDto.getNewEmail(),
                    requestDto.getNewStoreId(),
                    requestDto.getNewActive(),
                    requestDto.getNewUsername(),
                    requestDto.getNewPassword())
            );
        } catch (OutOfBoundsException | UnknownStoreException | UnknownAddressException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
