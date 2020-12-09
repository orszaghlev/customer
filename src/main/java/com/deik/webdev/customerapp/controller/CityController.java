package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CityDto;
import com.deik.webdev.customerapp.dto.CityUpdateRequestDto;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import com.deik.webdev.customerapp.service.CityService;
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
public class CityController {

    private final CityService service;

    @GetMapping("/city")
    public Collection<CityDto> listCities() {
        return service.getAllCity()
                .stream()
                .map(model -> CityDto.builder()
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/city/{country}")
    public Collection<CityDto> listCitiesByCountry(String country) {
        try {
            return service.getCitiesByCountry(country)
                    .stream()
                    .map(model -> CityDto.builder()
                            .city(model.getCity())
                            .country(model.getCountry())
                            .build())
                    .collect(Collectors.toList());
        } catch (EmptyException | UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/city")
    public void recordCity(@RequestBody CityDto requestDto) {
        try {
            service.recordCity(new City(
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/city")
    public void deleteCity(@RequestBody CityDto requestDto) {
        try {
            service.deleteCity(new City(
                    requestDto.getCity(),
                    requestDto.getCountry()
            ));
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/city")
    public void updateCity(@RequestBody CityUpdateRequestDto requestDto) {
        try {
            service.updateCity(new City(
                            requestDto.getCity(),
                            requestDto.getCountry()),
                    new City(
                            requestDto.getNewCity(),
                            requestDto.getNewCountry())
            );
        } catch (UnknownCountryException | UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
