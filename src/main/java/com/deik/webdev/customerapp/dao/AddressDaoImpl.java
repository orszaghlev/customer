package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.entity.CountryEntity;
import com.deik.webdev.customerapp.exception.UnknownAddressException;
import com.deik.webdev.customerapp.exception.UnknownCountryException;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.repository.AddressRepository;
import com.deik.webdev.customerapp.repository.CityRepository;
import com.deik.webdev.customerapp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
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
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public void createAddress(Address address) throws UnknownCountryException {
        AddressEntity addressEntity;
        GeometryFactory geometryFactory = new GeometryFactory();

        addressEntity = AddressEntity.builder()
                .address(address.getAddress())
                .address2(address.getAddress2())
                .district(address.getDistrict())
                .postalCode(address.getPostalCode())
                .phone(address.getPhone())
                .location(geometryFactory.createPoint(new Coordinate()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .city(queryCity(address.getCity(), address.getCountry()))
                .build();
        log.info("AddressEntity: {}", addressEntity);
        try {
            addressRepository.save(addressEntity);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected CityEntity queryCity(String city, String country) throws UnknownCountryException {
        Optional<CityEntity> cityEntity = cityRepository.findByCity(city);
        if (!cityEntity.isPresent()) {
            Optional<CountryEntity> countryEntity = countryRepository.findByCountry(country);
            if (!countryEntity.isPresent()) {
                throw new UnknownCountryException(country);
            }
            cityEntity = Optional.ofNullable(CityEntity.builder()
                    .city(city)
                    .country(countryEntity.get())
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            cityRepository.save(cityEntity.get());
            log.info("Recorded new City: {}, {}", city, country);
        }
        log.trace("CityEntity: {}", cityEntity);
        return cityEntity.get();
    }

    @Override
    public Collection<Address> readAll() {
        log.info("Read all addresses");
        return StreamSupport.stream(addressRepository.findAll().spliterator(),false)
                .map(entity -> new Address(
                        entity.getAddress(),
                        entity.getAddress2(),
                        entity.getDistrict(),
                        entity.getCity().getCity(),
                        entity.getCity().getCountry().getCountry(),
                        entity.getPostalCode(),
                        entity.getPhone()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Address> readAddressesByCity(String city) throws UnknownAddressException {
        Optional<CityEntity> cityEntity = cityRepository.findByCity(city);
        Collection<AddressEntity> addressEntity = addressRepository.findByCity(cityEntity);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by city)");
            return StreamSupport.stream(addressRepository.findByCity(cityEntity).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getCity().getCountry().getCountry(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Address> readAddressesByDistrict(String district) throws UnknownAddressException {
        Collection<AddressEntity> addressEntity = addressRepository.findByDistrict(district);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by district)");
            return StreamSupport.stream(addressRepository.findByDistrict(district).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getCity().getCountry().getCountry(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Address> readAddressesByPostalCode(String postalCode) throws UnknownAddressException {
        Collection<AddressEntity> addressEntity = addressRepository.findByPostalCode(postalCode);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by postal code)");
            return StreamSupport.stream(addressRepository.findByPostalCode(postalCode).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getCity().getCountry().getCountry(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = StreamSupport.stream(addressRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return address.getAddress().equals(entity.getAddress())  &&
                            address.getAddress2().equals(entity.getAddress2()) &&
                            address.getDistrict().equals(entity.getDistrict()) &&
                            address.getCity().equals(entity.getCity().getCity()) &&
                            address.getCountry().equals(entity.getCity().getCountry().getCountry());
                }
        ).findAny();
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException(String.format("Address Not Found %s", address), address);
        }
        addressRepository.delete(addressEntity.get());
        log.info("Deleted address: " + addressEntity.toString());
    }

    @Override
    public void updateAddress(Address address, Address newAddress) throws UnknownCountryException, UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address.getAddress());
        GeometryFactory geometryFactory = new GeometryFactory();
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException(String.format("Address Not Found %s", address), address);
        }
        log.info("Original: " + addressEntity.toString());
        addressEntity.get().setAddress(newAddress.getAddress());
        addressEntity.get().setAddress2(newAddress.getAddress2());
        addressEntity.get().setDistrict(newAddress.getDistrict());
        addressEntity.get().setCity(queryCity(newAddress.getCity(), newAddress.getCountry()));
        addressEntity.get().setPostalCode(newAddress.getPostalCode());
        addressEntity.get().setPhone(newAddress.getPhone());
        addressEntity.get().setLocation(geometryFactory.createPoint(new Coordinate()));
        addressEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + addressEntity.toString());
        try {
            addressRepository.save(addressEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
