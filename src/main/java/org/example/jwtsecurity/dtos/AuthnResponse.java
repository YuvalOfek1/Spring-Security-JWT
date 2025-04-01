package org.example.jwtsecurity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthnResponse {
    private String jwt;
}
