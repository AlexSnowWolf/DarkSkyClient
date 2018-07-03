package com.siic.alex.darkskyclient.events;

/**
 * Created by Alex on 23/2/2018.
 */

public class ErrorEvent {
    private final String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
