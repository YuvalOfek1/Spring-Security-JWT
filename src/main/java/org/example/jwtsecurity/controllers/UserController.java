package org.example.jwtsecurity.controllers;

import lombok.RequiredArgsConstructor;
import org.example.jwtsecurity.dao.UsersRepository;
import org.example.jwtsecurity.dtos.AuthnRequest;
import org.example.jwtsecurity.dtos.AuthnResponse;
import org.example.jwtsecurity.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody AuthnRequest registerRequest) {
        User user = usersRepository.findById(registerRequest.getUsername()).orElse(null);
        if(user!=null) {
            throw new ResourceAccessException("User already exists");
        }


        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user = new User(registerRequest.getUsername(), encodedPassword);
        usersRepository.save(user);
        return user.getUsername();


    }
}
