package org.subhankar.userservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.subhankar.userservice.model.DO.UserDO;
import org.subhankar.userservice.model.DTO.ResponseDTO;
import org.subhankar.userservice.model.DTO.UserRequestDTO;

public interface UserService {
    ResponseEntity<ResponseDTO> addUser(UserRequestDTO userRequestDTO);
    UserDO getUserByEmail(String email);
    ResponseEntity<ResponseDTO> getAllUsers();
    ResponseEntity<ResponseDTO> deleteUserByEmail(String email);
    ResponseEntity<ResponseDTO> updateUserByEmail(String email, UserRequestDTO userRequestDTO);
    ResponseEntity<ResponseDTO> updateUser(String id, UserRequestDTO userRequestDTO);

    ResponseEntity<ResponseDTO> deleteUser(String id);

    ResponseEntity<ResponseDTO> getUser(String id);

}
