package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Store;
import lombok.Data;

@Data
public class UnknownStoreException extends Exception {

    private Store store;

    public UnknownStoreException(Store store) {
        this.store = store;
    }

    public UnknownStoreException(String message, Store store) {
        super(message);
        this.store = store;
    }

    public UnknownStoreException(String store) {
        super(store);
    }

}
