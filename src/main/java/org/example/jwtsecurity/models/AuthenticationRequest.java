package org.example.jwtsecurity.models;


import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
