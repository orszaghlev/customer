package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Staff;
import lombok.Data;

@Data
public class UnknownStaffException {

    private Staff staff;

    public UnknownStaffException() {
    }

    public UnknownCountryException(String message, Staff staff) {
        super(message);
        this.staff = staff;
    }

}
