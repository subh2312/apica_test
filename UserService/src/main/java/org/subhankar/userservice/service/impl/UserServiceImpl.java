package org.subhankar.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.subhankar.userservice.exception.BadRequestException;
import org.subhankar.userservice.exception.ResourceNotFoundException;
import org.subhankar.userservice.model.DO.RoleDO;
import org.subhankar.userservice.model.DO.UserDO;
import org.subhankar.commonDTO.JournalDTO;
import org.subhankar.userservice.model.DTO.ResponseDTO;
import org.subhankar.userservice.model.DTO.UserRequestDTO;
import org.subhankar.userservice.model.DTO.UserResponseDTO;
import org.subhankar.userservice.repository.RoleRepository;
import org.subhankar.userservice.repository.UserRepository;
import org.subhankar.userservice.service.KafkaService;
import org.subhankar.userservice.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final KafkaService kafkaService;
    @Override
    public ResponseEntity<ResponseDTO> addUser(UserRequestDTO userRequestDTO) {
        List<String> errors = isValidUser(userRequestDTO);
        if(!errors.isEmpty()){
            String errorMessage = String.join(" , ", errors);
            throw new BadRequestException("Invalid User Details : "+errorMessage);
        }
        RoleDO roleDO = roleRepository.findByName(userRequestDTO.getRole().toLowerCase()).orElseThrow(()->new ResourceNotFoundException("Role","role",userRequestDTO.getRole()));
        Set<RoleDO> roles = Set.of(roleDO);
        UserDO userDO = repository.save(UserDO.builder()
                .email(userRequestDTO.getEmail())
                .fullName(userRequestDTO.getFullName())
                .password(encoder.encode(userRequestDTO.getPassword()))
                        .role(roles)
                .build());
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Added Successfully").status("201").data(
                UserResponseDTO.builder()
                        .id(userDO.getId())
                        .email(userDO.getEmail())
                        .fullName(userDO.getFullName())
                        .build()
        ).build();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("New User Registered with Email "+userDO.getEmail())
                .createdBy(userDO.getId())
                .role(roleDO.getName())
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    private List<String> isValidUser(UserRequestDTO userRequestDTO) {
        List<String> errors = new ArrayList<>();
        if(userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isEmpty()){
            errors.add("Email cannot be empty");
        }

        if(userRequestDTO.getFullName() == null || userRequestDTO.getFullName().isEmpty()){
            errors.add("Full Name cannot be empty");
        }

        if(userRequestDTO.getPassword() == null || userRequestDTO.getPassword().isEmpty()){
            errors.add("Password cannot be empty");
        }



        return errors;
    }

    @Override
    public UserDO getUserByEmail(String email) {
        UserDO userDO = repository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Fetched with Email "+userDO.getEmail())
                .createdBy(userDO.getId())
                .role(userDO.getRole().stream().findFirst().get().getName())
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return userDO;


    }

    @Override
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<UserResponseDTO> userDTOList = repository.findAll()
                .stream()
                .map(userDO -> UserResponseDTO.builder()
                        .id(userDO.getId())
                        .email(userDO.getEmail())
                        .fullName(userDO.getFullName())
                        .build())
                .toList();
        ResponseDTO responseDTO = ResponseDTO.builder().message("Users Found").status("200").data(userDTOList).build();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("All Users Fetched")
                .createdBy("Admin")
                .role("Admin")
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteUserByEmail(String email) {
        UserDO userDO = repository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        repository.delete(userDO);
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Deleted Successfully").status("200").data(null).build();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Deleted with Email "+email)
                .createdBy(userDO.getId())
                .role(userDO.getRole().stream().findFirst().get().getName())
                .createdAt(new Date())
                .build();

        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUserByEmail(String email, UserRequestDTO userDTO) {
        UserDO existingUser = repository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        updatePropertyIfNotEmpty(userDTO.getFullName(), existingUser::setFullName);
        updatePropertyIfNotEmpty(userDTO.getEmail(), existingUser::setEmail);
        updatePropertyIfNotEmpty(userDTO.getPassword(), existingUser::setPassword);

        UserDO updatedUser = repository.save(existingUser);
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Updated Successfully").status("200").data(
                UserResponseDTO.builder()
                        .id(updatedUser.getId())
                        .email(updatedUser.getEmail())
                        .fullName(updatedUser.getFullName())
                        .build()
        ).build();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Updated with Email "+email)
                .createdBy(updatedUser.getEmail())
                .role(updatedUser.getRole().toString())
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUser(String id, UserRequestDTO userDTO) {
        UserDO existingUser = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        updatePropertyIfNotEmpty(userDTO.getFullName(), existingUser::setFullName);
        updatePropertyIfNotEmpty(userDTO.getEmail(), existingUser::setEmail);
        updatePropertyIfNotEmpty(userDTO.getPassword(), existingUser::setPassword);

        UserDO updatedUser = repository.save(existingUser);
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Updated Successfully").status("200").data(
                UserResponseDTO.builder()
                        .id(updatedUser.getId())
                        .email(updatedUser.getEmail())
                        .fullName(updatedUser.getFullName())
                        .build()
        ).build();

        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Updated with id "+id)
                .createdBy(updatedUser.getEmail())
                .role(updatedUser.getRole().toString())
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteUser(String id) {
        UserDO userDO = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        repository.delete(userDO);
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Deleted with id "+id)
                .createdBy(userDO.getId())
                .role(userDO.getRole().stream().findFirst().get().getName())
                .createdAt(new Date())
                .build();
        kafkaService.updateTransaction(journalDTO);
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Deleted Successfully").status("200").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> getUser(String id) {
        UserDO userDO = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        ResponseDTO responseDTO = ResponseDTO.builder().message("User Found").status("200").data(
                UserResponseDTO.builder()
                        .id(userDO.getId())
                        .email(userDO.getEmail())
                        .fullName(userDO.getFullName())
                        .build()
        ).build();
        JournalDTO journalDTO = JournalDTO.builder()
                .message("User Fetched Using id "+id)
                .createdBy(userDO.getId())
                .role(userDO.getRole().stream().findFirst().get().getName())
                .createdAt(new Date())
                .build();

        kafkaService.updateTransaction(journalDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    private static void updatePropertyIfNotEmpty(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
}
