package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.entity.CountryEntity;
import com.deik.webdev.customerapp.exception.EmptyException;
import com.deik.webdev.customerapp.exception.OutOfBoundsException;
import com.deik.webdev.customerapp.exception.UnknownCityException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
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
    public void createCity(City city) throws UnknownCountryException {
        CityEntity cityEntity;

        cityEntity = CityEntity.builder()
                .city(city.getCity())
                .country(queryCountry(city.getCountry()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CityEntity: {}", cityEntity);
        try {
            cityRepository.save(cityEntity);
            log.info("Recorded new City: {}", city);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected CountryEntity queryCountry(String country) throws UnknownCountryException {
        Optional<CountryEntity> countryEntity = countryRepository.findByCountry(country);
        if (!countryEntity.isPresent()) {
            throw new UnknownCountryException("No Country Found");
        }
        log.trace("CountryEntity: {}", countryEntity);
        return countryEntity.get();
    }

    private void correctValue(int value) throws OutOfBoundsException {
        if (value <= 0) {
            throw new OutOfBoundsException("Value can't be smaller than 1!");
        }
    }

    @Override
    public Collection<City> readAll() {
        log.info("Read all cities");
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .map(entity -> new City(
                        entity.getId(),
                        entity.getCity(),
                        entity.getCountry().getCountry()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<City> readCitiesByCountry(String country) throws UnknownCityException, EmptyException {
        if (country == null) {
            throw new EmptyException("Add a country!");
        }
        Optional<CountryEntity> countryEntity = countryRepository.findByCountry(country);
        Collection<CityEntity> cityEntity = cityRepository.findByCountry(countryEntity);
        if (cityEntity.isEmpty()) {
            throw new UnknownCityException("No Cities Found");
        }
        else {
            log.info("Read all cities (by country)");
            return StreamSupport.stream(cityRepository.findByCountry(countryEntity).spliterator(),false)
                    .map(entity -> new City(
                            entity.getId(),
                            entity.getCity(),
                            entity.getCountry().getCountry()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public City readCityById(Integer id) throws UnknownCityException, EmptyException, OutOfBoundsException {
        if (id == null) {
            throw new EmptyException("Add an ID!");
        }
        correctValue(id);
        Optional<CityEntity> cityEntity = cityRepository.findById(id);
        if (!cityEntity.isPresent()) {
            throw new UnknownCityException("No City Found");
        }
        else {
            log.info("Read city (by ID)");
            return new City(
                    cityEntity.get().getId(),
                    cityEntity.get().getCity(),
                    cityEntity.get().getCountry().getCountry()
            );
        }
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException {
        Optional<CityEntity> cityEntity = StreamSupport.stream(cityRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return city.getId() == entity.getId() &&
                            city.getCity().equals(entity.getCity()) &&
                            city.getCountry().equals(entity.getCountry().getCountry());
                }
        ).findAny();
        if (!cityEntity.isPresent()) {
            throw new UnknownCityException(String.format("City Not Found %s", city), city);
        }
        cityRepository.delete(cityEntity.get());
        log.info("Deleted city: " + cityEntity.toString());
    }

    @Override
    public void updateCity(City city, City newCity) throws UnknownCountryException, UnknownCityException {
        Optional<CityEntity> cityEntity = cityRepository.findById(city.getId());
        if (!cityEntity.isPresent()) {
            throw new UnknownCityException(String.format("City Not Found %s", city), city);
        }
        log.info("Original: " + cityEntity.toString());
        cityEntity.get().setCity(newCity.getCity());
        cityEntity.get().setCountry(queryCountry(newCity.getCountry()));
        cityEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + cityEntity.toString());
        try {
            cityRepository.save(cityEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
