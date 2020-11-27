package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.CityDto;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.City;
import com.deik.webdev.customerapp.service.CityService;
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
public class CityController {

    private final CityService service;

    @GetMapping("/city")
    public Collection<CityDto> listCities(){
        return service.getAllCity()
                .stream()
                .map(model -> CityDto.builder()
                        .name(model.getName())
                        .country(model.getCountry())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/city")
    public void record(@RequestBody CityDto requestDto) {
        try {
            service.recordCity(new City(
                    requestDto.getName(),
                    requestDto.getCountry()
            ));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
