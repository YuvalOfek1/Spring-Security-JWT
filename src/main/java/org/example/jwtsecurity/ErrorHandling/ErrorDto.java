package org.example.jwtsecurity.ErrorHandling;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private int errorCode;
    private String errorMessage;
}
