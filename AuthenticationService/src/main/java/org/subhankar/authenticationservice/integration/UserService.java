package org.subhankar.authenticationservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subhankar.authenticationservice.model.DO.UserDO;
import org.subhankar.authenticationservice.model.DTO.GetUserByEmailDTO;
import org.subhankar.authenticationservice.model.DTO.ResponseDTO;
import org.subhankar.authenticationservice.model.DTO.UserRequestDTO;

@FeignClient(name = "UserService")
public interface UserService {

    @PostMapping("/users/email")
    UserDO getUserByEmail(@RequestBody GetUserByEmailDTO getUserByEmailDTO);

    @PostMapping("/users/new")
    ResponseEntity<ResponseDTO> addUser(@RequestBody UserRequestDTO userRequestDTO);

    @DeleteMapping("/users/{id}")
    ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id);
}
