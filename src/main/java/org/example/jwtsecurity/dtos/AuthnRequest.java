package org.example.jwtsecurity.dtos;

import lombok.Data;

@Data
public class AuthnRequest {
    private String username;
    private String password;
}
