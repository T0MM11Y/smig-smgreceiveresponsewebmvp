package com.smig.smg.receiver.exception;

import com.smig.smg.receiver.dto.common.ApiResponse;
import com.smig.smg.receiver.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler
 * 
 * @author SMIG Development Team
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SmgApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleSmgApiException(SmgApiException ex) {
        log.error("SMG API Exception: {}", ex.getMessage(), ex);
        
        ApiResponse<Object> response = ApiResponse.error(ex.getCode(), ex.getMessage());
        
        HttpStatus status = switch (ex.getCode()) {
            case Constants.ErrorCodes.NOT_FOUND -> HttpStatus.NOT_FOUND;
            case Constants.ErrorCodes.BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation Exception: {}", ex.getMessage(), ex);
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String message = Constants.Messages.INVALID_REQUEST + ": " + errors.toString();
        ApiResponse<Object> response = ApiResponse.error(Constants.ErrorCodes.BAD_REQUEST, message);
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ApiResponse<Object> response = ApiResponse.error(
            Constants.ErrorCodes.INTERNAL_ERROR, 
            "Internal server error occurred"
        );
        
        return ResponseEntity.internalServerError().body(response);
    }
}
