package com.wowfs.customermanagement.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationErrorResponse {
    
    private java.time.LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    // Field-specific errors for easy debugging
    private Map<String, String> fieldErrors;
}