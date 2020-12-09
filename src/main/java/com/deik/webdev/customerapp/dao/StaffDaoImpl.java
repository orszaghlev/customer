package com.deik.webdev.customerapp.dao;

import com.deik.webdev.customerapp.entity.*;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Staff;
import com.deik.webdev.customerapp.repository.*;
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
public class StaffDaoImpl implements StaffDao {

    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    @Override
    public void createStaff(Staff staff) throws UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        StaffEntity staffEntity;
        activeValue(staff.getActive());
        correctValue(staff.getId());
        correctValue(staff.getStoreId());

        staffEntity = StaffEntity.builder()
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .address(queryAddress(staff.getAddress()))
                .email(staff.getEmail())
                .store(queryStore(staff.getStoreId()))
                .active(staff.getActive())
                .username(staff.getUsername())
                .password(staff.getPassword())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("StaffEntity: {}", staffEntity);
        try {
            staffRepository.save(staffEntity);
            log.info("Recorded new Staff: {}", staff);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected AddressEntity queryAddress(String address) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddress(address);
        if (!addressEntity.isPresent()) {
            throw new UnknownAddressException("No Address Found");
        }
        else {
            log.trace("AddressEntity: {}", addressEntity);
            return addressEntity.get();
        }
    }

    protected StoreEntity queryStore(int storeId) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);
        if (!storeEntity.isPresent()) {
            throw new UnknownStoreException("No Store Found");
        }
        else {
            log.trace("StoreEntity: {}", storeEntity);
            return storeEntity.get();
        }
    }

    private void activeValue(int active) throws OutOfBoundsException {
        if (active != 0 && active != 1) {
            throw new OutOfBoundsException("Value of 'active' must be 0 or 1!");
        }
    }

    private void correctValue(int value) throws OutOfBoundsException {
        if (value <= 0) {
            throw new OutOfBoundsException("Value can't be smaller than 1!");
        }
    }

    @Override
    public Collection<Staff> readAll() {
        log.info("Read all staff");
        return StreamSupport.stream(staffRepository.findAll().spliterator(),false)
                .map(entity -> new Staff(
                        entity.getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getAddress().getAddress(),
                        entity.getEmail(),
                        entity.getStore().getId(),
                        entity.getActive(),
                        entity.getUsername(),
                        entity.getPassword()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Staff> readStaffByFirstNameAndLastName(String firstName, String lastName) throws UnknownStaffException, EmptyException {
        if (firstName == null && lastName == null) {
            throw new EmptyException("Add a first name and a last name!");
        }
        if ((firstName != null && lastName == null) || (firstName == null && lastName != null)) {
            throw new EmptyException("You forgot to add a first name or a last name!");
        }
        Collection<StaffEntity> staffEntity = staffRepository.findByFirstNameAndLastName(firstName, lastName);
        if (staffEntity.isEmpty()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.info("Read all staff (by username)");
            return StreamSupport.stream(staffRepository.findByFirstNameAndLastName(firstName, lastName).spliterator(),false)
                    .map(entity -> new Staff(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getAddress().getAddress(),
                            entity.getEmail(),
                            entity.getStore().getId(),
                            entity.getActive(),
                            entity.getUsername(),
                            entity.getPassword()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Staff> readStaffByEmail(String email) throws UnknownStaffException, EmptyException {
        if (email == null) {
            throw new EmptyException("Add an email!");
        }
        Collection<StaffEntity> staffEntity = staffRepository.findByEmail(email);
        if (staffEntity.isEmpty()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.info("Read all staff (by email)");
            return StreamSupport.stream(staffRepository.findByEmail(email).spliterator(),false)
                    .map(entity -> new Staff(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getAddress().getAddress(),
                            entity.getEmail(),
                            entity.getStore().getId(),
                            entity.getActive(),
                            entity.getUsername(),
                            entity.getPassword()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Staff> readStaffByStoreId(Integer storeId) throws UnknownStaffException, EmptyException, OutOfBoundsException {
        if (storeId == null) {
            throw new EmptyException("Add a store ID!");
        }
        correctValue(storeId);
        Collection<StaffEntity> staffEntity = staffRepository.findByStoreId(storeId);
        if (staffEntity.isEmpty()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.info("Read all staff (by store ID)");
            return StreamSupport.stream(staffRepository.findByStoreId(storeId).spliterator(),false)
                    .map(entity -> new Staff(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getAddress().getAddress(),
                            entity.getEmail(),
                            entity.getStore().getId(),
                            entity.getActive(),
                            entity.getUsername(),
                            entity.getPassword()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<Staff> readActiveStaff(Integer active) throws UnknownStaffException, OutOfBoundsException, EmptyException {
        if (active == null) {
            throw new EmptyException("Add 0 (inactive) or 1 (active)!");
        }
        activeValue(active);
        Collection<StaffEntity> staffEntity = staffRepository.findByActive(active);
        if (staffEntity.isEmpty()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.info("Read all staff (by store ID)");
            return StreamSupport.stream(staffRepository.findByActive(active).spliterator(),false)
                    .map(entity -> new Staff(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getAddress().getAddress(),
                            entity.getEmail(),
                            entity.getStore().getId(),
                            entity.getActive(),
                            entity.getUsername(),
                            entity.getPassword()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Staff readStaffById(Integer id) throws UnknownStaffException, EmptyException, OutOfBoundsException {
        if (id == null) {
            throw new EmptyException("Add an ID!");
        }
        correctValue(id);
        Optional<StaffEntity> staffEntity = staffRepository.findById(id);
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException("No Staff Found");
        }
        else {
            log.info("Read staff (by ID)");
            return new Staff(
                    staffEntity.get().getId(),
                    staffEntity.get().getFirstName(),
                    staffEntity.get().getLastName(),
                    staffEntity.get().getAddress().getAddress(),
                    staffEntity.get().getEmail(),
                    staffEntity.get().getStore().getId(),
                    staffEntity.get().getActive(),
                    staffEntity.get().getUsername(),
                    staffEntity.get().getPassword()
            );
        }
    }

    @Override
    public void deleteStaff(Staff staff) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = StreamSupport.stream(staffRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return staff.getId() == entity.getId() &&
                            staff.getFirstName().equals(entity.getFirstName()) &&
                            staff.getLastName().equals(entity.getLastName()) &&
                            staff.getAddress().equals(entity.getAddress().getAddress()) &&
                            staff.getEmail().equals(entity.getEmail()) &&
                            staff.getStoreId() == entity.getStore().getId() &&
                            staff.getActive() == entity.getActive() &&
                            staff.getUsername().equals(entity.getUsername()) &&
                            staff.getPassword().equals(entity.getPassword());
                }
        ).findAny();
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(String.format("Staff Not Found %s", staff));
        }
        staffRepository.delete(staffEntity.get());
        log.info("Deleted staff: " + staffEntity.toString());
    }

    @Override
    public void updateStaff(Staff staff, Staff newStaff) throws UnknownStoreException, UnknownAddressException, UnknownStaffException, OutOfBoundsException {
        Optional<StaffEntity> staffEntity = staffRepository.findById(staff.getId());
        if (!staffEntity.isPresent()) {
            throw new UnknownStaffException(String.format("Staff Not Found %s", staff), staff);
        }
        activeValue(newStaff.getActive());
        correctValue(newStaff.getId());
        correctValue(newStaff.getStoreId());
        log.info("Original: " + staffEntity.toString());
        staffEntity.get().setFirstName(newStaff.getFirstName());
        staffEntity.get().setLastName(newStaff.getLastName());
        staffEntity.get().setAddress(queryAddress(newStaff.getAddress()));
        staffEntity.get().setEmail(newStaff.getEmail());
        staffEntity.get().setStore(queryStore(newStaff.getStoreId()));
        staffEntity.get().setActive(newStaff.getActive());
        staffEntity.get().setUsername(newStaff.getUsername());
        staffEntity.get().setPassword(newStaff.getPassword());
        staffEntity.get().setLastUpdate(new Timestamp((new Date()).getTime()));
        log.info("Updated: " + staffEntity.toString());
        try {
            staffRepository.save(staffEntity.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
