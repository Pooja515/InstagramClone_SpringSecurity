/**
package com.insta.instagram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.User;
import com.insta.instagram.service.UserService;
import com.insta.instagram.repository.UserRepository;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
   
    private UserRepository userRepo;
    
    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException{
    	
    User createdUser = userService.registerUser(user);
    
    return new ResponseEntity<User>(createdUser,HttpStatus.OK);
    }
    
    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth )throws BadCredentialsException{
    	
    	Optional<User> opt = userRepo.findByEmail(auth.getName());
    	
    	if(opt.isPresent()) {
    		return new ResponseEntity<User>(opt.get(),HttpStatus.ACCEPTED);
    	}
    	throw new BadCredentialsException("invalid username or password");
    }
}

*/




package com.insta.instagram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.User;
import com.insta.instagram.service.UserService;
import com.insta.instagram.repository.UserRepository;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo; // Add the @Autowired annotation to properly inject UserRepository

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) {
        try {
            Optional<User> opt = userRepo.findByEmail(auth.getName());
            if (opt.isPresent()) {
                return new ResponseEntity<>(opt.get(), HttpStatus.ACCEPTED);
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        } catch (BadCredentialsException e) {
            // Handle BadCredentialsException
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Handle other exceptions if necessary
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

