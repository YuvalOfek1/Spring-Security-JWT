package org.example.jwtsecurity.controllers;

import lombok.RequiredArgsConstructor;
import org.example.jwtsecurity.dtos.AuthnRequest;
import org.example.jwtsecurity.dtos.AuthnResponse;
import org.example.jwtsecurity.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthnController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    @PostMapping(("/login"))
    public AuthnResponse createAuthnToken(@RequestBody AuthnRequest authnRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authnRequest.getUsername(), authnRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username/password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authnRequest.getUsername());
        final String jwt = JwtUtil.generateToken(userDetails);
        return new AuthnResponse(jwt);
    }

}

