package org.subhankar.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.subhankar.userservice.model.DTO.ResponseDTO;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestException(BadRequestException e){
        return new ResponseEntity<>(ResponseDTO.builder().message(e.getMessage()).status("400").data(null).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(ResponseDTO.builder().message(e.getMessage()).status("404").data(null).build(), HttpStatus.NOT_FOUND);
    }
}
