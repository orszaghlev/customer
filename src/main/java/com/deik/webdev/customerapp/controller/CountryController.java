package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CountryDto;
import com.deik.webdev.customerapp.dto.CountryUpdateRequestDto;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
import com.deik.webdev.customerapp.service.CountryService;
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
public class CountryController {

    private final CountryService service;

    @GetMapping("/country")
    public Collection<CountryDto> listCountries() {
        return service.getAllCountry()
                .stream()
                .map(model -> CountryDto.builder()
                        .id(model.getId())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/country/{id}")
    public CountryDto listCountryById(Integer id) {
        try {
            Country country = service.getCountryById(id);
            return new CountryDto(
                    country.getId(),
                    country.getCountry());
        } catch (OutOfBoundsException | EmptyException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/country")
    public void recordCountry(@RequestBody CountryDto requestDto) {
        try {
            service.recordCountry(new Country(
                    requestDto.getId(),
                    requestDto.getCountry()
            ));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/country")
    public void deleteCountry(@RequestBody CountryDto requestDto) {
        try {
            service.deleteCountry(new Country(
                    requestDto.getId(),
                    requestDto.getCountry()
            ));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/country")
    public void updateCountry(@RequestBody CountryUpdateRequestDto requestDto) {
        try {
            service.updateCountry(new Country(
                            requestDto.getId(),
                            requestDto.getCountry()),
                    new Country(
                            requestDto.getId(),
                            requestDto.getNewCountry())
            );
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
