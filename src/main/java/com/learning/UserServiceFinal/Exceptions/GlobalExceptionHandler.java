package com.learning.UserServiceFinal.Exceptions;

import com.learning.UserServiceFinal.DTOs.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(
                new ExceptionDTO(notFoundException.getMessage(),
                HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionDTO> handleAlreadyExistsException(AlreadyExistsException alreadyExistsException){
        return new ResponseEntity<>(
                new ExceptionDTO(alreadyExistsException.getMessage(),
                        HttpStatus.CONFLICT),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentialException(BadCredentialsException badCredentialsException){
        return new ResponseEntity<>(
                new ExceptionDTO(badCredentialsException.getMessage(),
                        HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ExceptionDTO> handleInvalidSessionException(InvalidSessionException invalidSessionException){
        return new ResponseEntity<>(
                new ExceptionDTO(invalidSessionException.getMessage(),
                        HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
}
