package org.subhankar.authenticationservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.subhankar.authenticationservice.model.DTO.LoginRequestDTO;
import org.subhankar.authenticationservice.model.DTO.ResponseDTO;
import org.subhankar.authenticationservice.model.DTO.UserRequestDTO;
import org.subhankar.authenticationservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request) {

        return authenticationService.login(loginRequestDTO, response, request);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletResponse response, HttpServletRequest request) {

        return authenticationService.logout(response, request);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {

        return authenticationService.register(userRequestDTO);
    }
}
