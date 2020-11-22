package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.entity.CountryEntity;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.model.City;
import com.deik.webdev.customerapp.repository.CityRepository;
import com.deik.webdev.customerapp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityDaoImpl implements CityDao {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public void createCity(City city) {
        CityEntity cityEntity;

        cityEntity = CityEntity.builder()
                .name(city.getName())
                .country(queryCountry(city.getCountry()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CityEntity: {}", cityEntity);
        try {
            cityRepository.save(cityEntity);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected CountryEntity queryCountry(String country) {

        Optional<CountryEntity> countryEntity = countryRepository.findByName(country);
        if (!countryEntity.isPresent()) {
            countryEntity = Optional.ofNullable(CountryEntity.builder()
                    .name(country)
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            countryRepository.save(countryEntity.get());
            log.info("Recorded new Country: {}", country);
        }
        log.trace("Country Entity: {}", countryEntity);
        return countryEntity.get();
    }

    @Override
    public Collection<City> readAll() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .map(entity -> new City(
                        entity.getName(),
                        entity.getCountry().getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException {
        Optional<CityEntity> cityEntity = StreamSupport.stream(cityRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return city.getName().equals(entity.getName()) &&
                            city.getCountry().equals(entity.getCountry().getName());
                }
        ).findAny();
        if (!cityEntity.isPresent()) {
            throw new UnknownCityException(String.format("City Not Found %s", city), city);
        }
        cityRepository.delete(cityEntity.get());
    }

}
