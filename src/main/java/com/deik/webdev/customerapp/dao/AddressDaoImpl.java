package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.AddressEntity;
import com.deik.webdev.customerapp.entity.CityEntity;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Address;
import com.deik.webdev.customerapp.repository.AddressRepository;
import com.deik.webdev.customerapp.repository.CityRepository;
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

    @Override
    public void createAddress(Address address) throws UnknownCityException {
        AddressEntity addressEntity;
        GeometryFactory geometryFactory = new GeometryFactory();

        addressEntity = AddressEntity.builder()
                .address(address.getAddress())
                .address2(address.getAddress2())
                .district(address.getDistrict())
                .city(queryCity(address.getCity()))
                .postalCode(address.getPostalCode())
                .phone(address.getPhone())
                .location(geometryFactory.createPoint(new Coordinate()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("AddressEntity: {}", addressEntity);
        try {
            addressRepository.save(addressEntity);
            log.info("Recorded new Address: {}", address);
        }
        catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    protected CityEntity queryCity(String city) throws UnknownCityException {
        Optional<CityEntity> cityEntity = cityRepository.findByCity(city);
        if (!cityEntity.isPresent()) {
            throw new UnknownCityException("No City Found");
        }
        log.trace("CityEntity: {}", cityEntity);
        return cityEntity.get();
    }

    private void correctValue(int value) throws OutOfBoundsException {
        if (value <= 0) {
            throw new OutOfBoundsException("ID can't be less than 1!");
        }
    }

    @Override
    public Collection<Address> readAll() {
        log.info("Read all addresses");
        return StreamSupport.stream(addressRepository.findAll().spliterator(),false)
                .map(entity -> new Address(
                        entity.getId(),
                        entity.getAddress(),
                        entity.getAddress2(),
                        entity.getDistrict(),
                        entity.getCity().getCity(),
                        entity.getPostalCode(),
                        entity.getPhone()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Address> readAddressesByCity(String city) throws UnknownAddressException, EmptyException {
        if (city == null) {
            throw new EmptyException("Add a city!");
        }
        Optional<CityEntity> cityEntity = cityRepository.findByCity(city);
        Collection<AddressEntity> addressEntity = addressRepository.findByCity(cityEntity);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by city)");
            return StreamSupport.stream(addressRepository.findByCity(cityEntity).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getId(),
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Address> readAddressesByDistrict(String district) throws UnknownAddressException, EmptyException {
        if (district == null) {
            throw new EmptyException("Add a district!");
        }
        Collection<AddressEntity> addressEntity = addressRepository.findByDistrict(district);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by district)");
            return StreamSupport.stream(addressRepository.findByDistrict(district).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getId(),
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Address> readAddressesByPostalCode(String postalCode) throws UnknownAddressException, EmptyException {
        if (postalCode == null) {
            throw new EmptyException("Add a postal code!");
        }
        Collection<AddressEntity> addressEntity = addressRepository.findByPostalCode(postalCode);
        if (addressEntity.isEmpty()) {
            throw new UnknownAddressException("No Addresses Found");
        }
        else {
            log.info("Read all addresses (by postal code)");
            return StreamSupport.stream(addressRepository.findByPostalCode(postalCode).spliterator(), false)
                    .map(entity -> new Address(
                            entity.getId(),
                            entity.getAddress(),
                            entity.getAddress2(),
                            entity.getDistrict(),
                            entity.getCity().getCity(),
                            entity.getPostalCode(),
                            entity.getPhone()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Address readAddressById(Integer id) throws UnknownAddressException, EmptyException, OutOfBoundsException {
        if (id == null) {
            throw new EmptyException("Add an ID!");
        }
        correctValue(id);
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException("No Address Found");
        }
        else {
            log.info("Read address (by ID)");
            return new Address(
                    addressEntity.get().getId(),
                    addressEntity.get().getAddress(),
                    addressEntity.get().getAddress2(),
                    addressEntity.get().getDistrict(),
                    addressEntity.get().getCity().getCity(),
                    addressEntity.get().getPostalCode(),
                    addressEntity.get().getPhone()
            );
        }
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = StreamSupport.stream(addressRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return address.getId() == entity.getId() &&
                            address.getAddress().equals(entity.getAddress())  &&
                            address.getAddress2().equals(entity.getAddress2()) &&
                            address.getDistrict().equals(entity.getDistrict()) &&
                            address.getCity().equals(entity.getCity().getCity()) &&
                            address.getPostalCode().equals(entity.getPostalCode()) &&
                            address.getPhone().equals(entity.getPhone());
                }
        ).findAny();
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException(String.format("Address Not Found %s", address), address);
        }
        addressRepository.delete(addressEntity.get());
        log.info("Deleted address: " + addressEntity.toString());
    }

    @Override
    public void updateAddress(Address address, Address newAddress) throws UnknownCityException, UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address.getAddress());
        GeometryFactory geometryFactory = new GeometryFactory();
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException(String.format("Address Not Found %s", address));
        }
        log.info("Original: " + addressEntity.toString());
        addressEntity.get().setAddress(newAddress.getAddress());
        addressEntity.get().setAddress2(newAddress.getAddress2());
        addressEntity.get().setDistrict(newAddress.getDistrict());
        addressEntity.get().setCity(queryCity(newAddress.getCity()));
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
