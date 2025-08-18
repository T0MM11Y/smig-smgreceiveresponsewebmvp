package com.smig.smg.receiver.exception;

/**
 * Custom application exception
 * 
 * @author SMIG Development Team
 */
public class SmgApiException extends RuntimeException {

    private final String code;

    public SmgApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SmgApiException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
