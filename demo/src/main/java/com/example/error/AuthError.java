package com.example.error;



import java.util.Date;

import lombok.Data;

@Data
public class AuthError {
    private String error_message;
    private int error_status;
    private Date timesDate_tamp;

    public AuthError(String error_message, int error_status) {
        this.error_message = error_message;
        this.error_status = error_status;
        this.timesDate_tamp = new Date();
    }
}