package com.deik.webdev.customerapp.exception;

import com.deik.webdev.customerapp.model.Staff;
import lombok.Data;

@Data
public class UnknownStaffException extends Exception {

    private Staff staff;

    public UnknownStaffException(Staff staff) {
        this.staff = staff;
    }

    public UnknownStaffException(String message, Staff staff) {
        super(message);
        this.staff = staff;
    }

    public UnknownStaffException(String staff) {
        super(staff);
    }

}
