package org.subhankar.authenticationservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
