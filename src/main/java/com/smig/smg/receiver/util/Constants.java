package com.smig.smg.receiver.util;

/**
 * Application constants
 * 
 * @author SMIG Development Team
 */
public final class Constants {

    private Constants() {
        // Utility class
    }

    public static final class Api {
        public static final String VERSION_V1 = "/api/v1";
        public static final String RESPONSES = "/responses";
        
        private Api() {}
    }

    public static final class Messages {
        public static final String RESPONSE_CREATED = "Response created successfully";
        public static final String RESPONSE_RETRIEVED = "Response retrieved successfully";
        public static final String RESPONSES_RETRIEVED = "Responses retrieved successfully";
        public static final String RESPONSE_NOT_FOUND = "Response message not found";
        public static final String INVALID_REQUEST = "Invalid request parameters";
        
        private Messages() {}
    }

    public static final class ErrorCodes {
        public static final String SUCCESS = "200";
        public static final String CREATED = "201";
        public static final String BAD_REQUEST = "400";
        public static final String NOT_FOUND = "404";
        public static final String INTERNAL_ERROR = "500";
        
        private ErrorCodes() {}
    }
}
