package com.learning.UserServiceFinal.Controllers;

import com.learning.UserServiceFinal.DTOs.*;
import com.learning.UserServiceFinal.Exceptions.AlreadyExistsException;
import com.learning.UserServiceFinal.Exceptions.BadCredentialsException;
import com.learning.UserServiceFinal.Exceptions.InvalidSessionException;
import com.learning.UserServiceFinal.Exceptions.NotFoundException;
import com.learning.UserServiceFinal.Models.SessionStatus;
import com.learning.UserServiceFinal.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO requestDTO) throws AlreadyExistsException {
        UserDTO userDTO = authService.signUp(requestDTO.getEmail(), requestDTO.getName(),
                requestDTO.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO requestDTO) throws NotFoundException, BadCredentialsException {
        return authService.login(requestDTO.getEmail(), requestDTO.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogOutRequestDTO requestDTO) throws InvalidSessionException {
        authService.logout(requestDTO.getToken(), requestDTO.getUserId());
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDTO requestDTO){
        SessionStatus sessionStatus = authService.validate(requestDTO.getToken(),
                requestDTO.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
