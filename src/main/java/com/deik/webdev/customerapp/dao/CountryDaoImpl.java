package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.CountryEntity;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Country;
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
public class CountryDaoImpl implements CountryDao {

    private final CountryRepository countryRepository;

    @Override
    public void createCountry(Country country) {
        CountryEntity countryEntity;

        countryEntity = CountryEntity.builder()
                .country(country.getCountry())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CountryEntity: {}", countryEntity);
        try {
            countryRepository.save(countryEntity);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Collection<Country> readAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .map(entity -> new Country(
                        entity.getCountry()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException {
        Optional<CountryEntity> countryEntity = StreamSupport.stream(countryRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return country.getCountry().equals(entity.getCountry());
                }
        ).findAny();
        if (!countryEntity.isPresent()) {
            throw new UnknownCountryException(String.format("Country Not Found %s", country));
        }
        countryRepository.delete(countryEntity.get());
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException {
        Optional<CountryEntity> countryEntity = countryRepository.findByCountry(country.getCountry());
        if (!countryEntity.isPresent()) {
            throw new UnknownCountryException(String.format("Country Not Found %s", country), country);
        }
        log.info("Original: " + countryEntity.toString());
        countryEntity.get().setCountry(newCountry.getCountry());
        countryEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + countryEntity.toString());
        try {
            countryRepository.save(countryEntity.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
