package com.insta.instagram.exceptions;

import java.time.LocalDateTime;
import java.time.LocalDate;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import com.insta.instagram.exceptions.UserException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptions {
	
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue, WebRequest req){
    	
    	ErrorDetails err = new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
    	
    	return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me){
    	
    	ErrorDetails err = new ErrorDetails(me.getBindingResult().getFieldError().getDefaultMessage(),"validation error",LocalDateTime.now());
    	
    	return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception ue, WebRequest req){
    	
    	ErrorDetails err = new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
    	
    	return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }
}
