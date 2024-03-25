package org.subhankar.journalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.subhankar.journalservice.model.DTO.ResponseDTO;

@ControllerAdvice
public class JournalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(ResponseDTO.builder().message(e.getMessage()).status("404").data(null).build(), HttpStatus.NOT_FOUND);
    }
}
