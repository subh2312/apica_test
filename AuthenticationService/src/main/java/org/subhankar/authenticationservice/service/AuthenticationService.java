package org.subhankar.authenticationservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.subhankar.authenticationservice.model.DTO.LoginRequestDTO;
import org.subhankar.authenticationservice.model.DTO.ResponseDTO;
import org.subhankar.authenticationservice.model.DTO.UserRequestDTO;

public interface AuthenticationService {
    ResponseEntity<ResponseDTO> login(LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<ResponseDTO> logout(HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<ResponseDTO> register(UserRequestDTO loginRequestDTO);
}
