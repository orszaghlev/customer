package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Store;
import lombok.Data;

@Data
public class UnknownStoreException {

    private Store store;

    public UnknownStoreException() {
    }

    public UnknownStoreException(String message, Store store) {
        super(message);
        this.store = store;
    }

}
