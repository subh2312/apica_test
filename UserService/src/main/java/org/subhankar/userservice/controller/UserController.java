package org.subhankar.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subhankar.userservice.model.DO.UserDO;
import org.subhankar.userservice.model.DTO.GetUserByEmailDTO;
import org.subhankar.userservice.model.DTO.ResponseDTO;
import org.subhankar.userservice.model.DTO.UserRequestDTO;
import org.subhankar.userservice.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.addUser(userRequestDTO);
    }

    @PostMapping("/email")
    public UserDO getUserByEmail(@RequestBody GetUserByEmailDTO getUserByEmailDTO){
        return userService.getUserByEmail(getUserByEmailDTO.getEmail());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/adm/{email}")
    public ResponseEntity<ResponseDTO> deleteUserByEmail(@PathVariable String email){
        return userService.deleteUserByEmail(email);
    }

    @PutMapping("/adm/{email}")
    public ResponseEntity<ResponseDTO> updateUserByEmail(@PathVariable String email, @RequestBody UserRequestDTO userRequestDTO){
        return userService.updateUserByEmail(email, userRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO userRequestDTO){
        return userService.updateUser(id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @GetMapping("/adm/{email}")
    public UserDO getUserByEmailId(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

}
