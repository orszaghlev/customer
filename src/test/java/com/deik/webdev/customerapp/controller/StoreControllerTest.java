package com.deik.webdev.customerapp.controller;

import com.deik.webdev.customerapp.dto.StoreDto;
import com.deik.webdev.customerapp.dto.StoreUpdateRequestDto;
import com.deik.webdev.customerapp.exception.*;
import com.deik.webdev.customerapp.model.Store;
import com.deik.webdev.customerapp.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreControllerTest {

    @InjectMocks
    private StoreController storeController;
    @Mock
    private StoreService storeService;

    @Test
    public void testListStores() {
        when(storeService.getAllStore()).thenReturn(getStores());
        storeController.listStores();

        verify(storeService, times(1)).getAllStore();
    }

    @Test
    public void testListStoresByStaff() throws UnknownStoreException, EmptyException {
        when(storeService.getStoresByStaff(any())).thenReturn(getStores());
        storeController.listStoresByStaff(anyString());

        verify(storeService, times(1)).getStoresByStaff(anyString());
    }

    @Test
    public void testListStoreById() throws UnknownStoreException, EmptyException, OutOfBoundsException {
        when(storeService.getStoreById(any())).thenReturn(getStore());
        storeController.listStoreById(anyInt());

        verify(storeService, times(1)).getStoreById(anyInt());
    }

    @Test
    public void testRecordStore() throws UnknownAddressException, UnknownStaffException, OutOfBoundsException {
        storeController.recordStore(getStoreDto());

        verify(storeService, times(1)).recordStore(getStore());
    }

    @Test
    public void testDeleteStore() throws UnknownStoreException {
        storeController.deleteStore(getStoreDto());

        verify(storeService, times(1)).deleteStore(any());
    }

    @Test
    public void testUpdateStore() throws UnknownStaffException, UnknownStoreException, UnknownAddressException, OutOfBoundsException {
        storeController.updateStore(getStoreUpdateRequestDto());

        verify(storeService, times(1)).updateStore(any(), any());
    }

    private Store getStore() {
        return new Store(
                1,
                "staff",
                "address"
        );
    }

    private StoreDto getStoreDto() {
        return new StoreDto(
                1,
                "staff",
                "address"
        );
    }

    private StoreUpdateRequestDto getStoreUpdateRequestDto() {
        return new StoreUpdateRequestDto(
                "staff",
                "address"
        );
    }

    private Collection<Store> getStores() {
        return Arrays.asList(
                new Store(
                        1,
                        "staff",
                        "address"
                ),
                new Store(
                        2,
                        "staff",
                        "address"
                ),
                new Store(
                        3,
                        "staff",
                        "address"
                ));
    }

}
