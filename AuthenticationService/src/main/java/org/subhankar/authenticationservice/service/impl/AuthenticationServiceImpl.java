package org.subhankar.authenticationservice.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.subhankar.authenticationservice.config.CustomAuthenticationProvider;
import org.subhankar.authenticationservice.config.JwtService;
import org.subhankar.authenticationservice.integration.UserService;
import org.subhankar.authenticationservice.model.DO.RoleDO;
import org.subhankar.authenticationservice.model.DO.UserDO;
import org.subhankar.authenticationservice.model.DTO.GetUserByEmailDTO;
import org.subhankar.authenticationservice.model.DTO.LoginRequestDTO;
import org.subhankar.authenticationservice.model.DTO.ResponseDTO;
import org.subhankar.authenticationservice.model.DTO.UserRequestDTO;
import org.subhankar.authenticationservice.service.AuthenticationService;
import org.subhankar.authenticationservice.service.KafkaService;
import org.subhankar.commonDTO.JournalDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomAuthenticationProvider authenticationManager;
    private final JwtService jwtProvider;
    private final UserService userIntegration;
    private final KafkaService kafkaService;
    @Override
    public ResponseEntity<ResponseDTO> login(LoginRequestDTO loginRequestDTO, HttpServletResponse response, HttpServletRequest request) {
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("token")){
                    ResponseDTO result = ResponseDTO.builder()
                            .status("200")
                            .message("User already logged in")
                            .data(cookie.getValue())
                            .build();
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            }
        }
        UserDO user = userIntegration.getUserByEmail(GetUserByEmailDTO.builder().email(loginRequestDTO.getEmail()).build());
        if(user == null){
            ResponseDTO result = ResponseDTO.builder()
                    .status("401")
                    .message("Unauthorized")
                    .build();
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
            ResponseDTO result=null;
            if(authentication.isAuthenticated()){
                Map<String, Object> claims = new HashMap<>();
                claims.put("name", user.getFullName());
                claims.put("id", user.getId());
                claims.put("role", user.getRole().stream().map(RoleDO::getName).toList());
                String accessToken = jwtProvider.generateToken(claims,user);
                Cookie cookie = new Cookie("token", accessToken);
                cookie.setMaxAge(3600);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");

                response.addCookie(cookie);
                result = ResponseDTO.builder()
                        .status("200")
                        .message("User logged in successfully")
                        .data(accessToken)
                        .build();

            } else {
                throw new UsernameNotFoundException("invalid user name or password..!!");
            }
            RoleDO roleDO = user.getRole().stream().findFirst().get();
            JournalDTO journalDTO = JournalDTO.builder()
                    .message("User logged in successfully")
                    .createdBy(user.getId())
                    .createdAt(new Date())
                    .role(roleDO.getName())
                    .build();
            kafkaService.updateTransaction(journalDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Log the exception or relevant details
            log.error("Bad credentials provided for login: {}"+ e.getMessage());
            throw e;
        }catch (Exception e) {
            // Log the exception or relevant details
            log.error("Exception occurred while logging in: {}"+ e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> logout(HttpServletResponse response, HttpServletRequest request) {
        String token = null;
        String userEmail = null;

        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null){
            ResponseDTO result = ResponseDTO.builder()
                    .status("401")
                    .message("Unauthorized")
                    .build();
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        response.setHeader(HttpHeaders.SET_COOKIE, "token=; Max-Age=0; Path=/; HttpOnly; SameSite=None; Secure");
        ResponseDTO result = ResponseDTO.builder()
                .status("200")
                .message("User logged out successfully")
                .build();
        UserDO user = userIntegration.getUserByEmail(GetUserByEmailDTO.builder().email(userEmail).build());
        RoleDO roleDO = user.getRole().stream().findFirst().get();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User logged out successfully")
                .createdBy(user.getId())
                .createdAt(new Date())
                .role(roleDO.getName())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> register(UserRequestDTO userRequestDTO) {
        return userIntegration.addUser(userRequestDTO);
    }
}
