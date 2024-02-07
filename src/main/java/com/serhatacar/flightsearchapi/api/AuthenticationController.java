package com.serhatacar.flightsearchapi.api;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IRoleEntityService;
import com.serhatacar.flightsearchapi.bussiness.abstracts.IUserEntityService;
import com.serhatacar.flightsearchapi.core.exception.UsernameInvalidException;
import com.serhatacar.flightsearchapi.dto.request.user.UserRequest;
import com.serhatacar.flightsearchapi.dto.response.AuthenticationResponse;
import com.serhatacar.flightsearchapi.entity.RoleEntity;
import com.serhatacar.flightsearchapi.entity.UserEntity;
import com.serhatacar.flightsearchapi.security.JWTGenerator;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IUserEntityService userEntityService;
    private IRoleEntityService roleEntityService;

    @Operation(
            summary = "Login",
            description = "Login to the system with username and password")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRequest userRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(), userRequestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

    private JWTGenerator jwtGenerator;


    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                    IUserEntityService userEntityService, IRoleEntityService roleEntityService,
                                    JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userEntityService = userEntityService;
        this.roleEntityService = roleEntityService;
        this.jwtGenerator = jwtGenerator;
    }

    @Operation(
            summary = "Register",
            description = "Register to the system with username and password")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {

        if (userEntityService.isUserExists(userRequest.getUsername())) {
            throw new UsernameInvalidException("Cannot add user. Username already exists.");
        }

        if (!userEntityService.isUserNameValid(userRequest.getUsername())) {
            throw new UsernameInvalidException("Cannot register user. Invalid username.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        RoleEntity role = roleEntityService.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userEntityService.save(user);

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }
}