package com.gamechanger.rai_server.controller;

import com.gamechanger.rai_server.dto.LoginUserDTO;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.jwt.JwtResponse;
import com.gamechanger.rai_server.service.JwtService;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import com.gamechanger.rai_server.utils.UserInfoDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    private final RequestValidator requestValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, RequestValidator requestValidator, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.requestValidator = requestValidator;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user) {
        log.info("user: {}", user);
        try {
            if (!requestValidator.validateUser(user)) {
                return ResponseEntity.badRequest().body("Please Enter a valid Request with username size between 4 to 10 and password size greater than 8");
            }
            userService.createUser(user);
            log.info("User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("User Saved Successfully with username " + user.getUserName());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        } catch (Exception ex) {
            log.error("Error creating user: ", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        try{
            log.info("LoginUserDTO: {}", loginUserDTO);
            UserInfoDetails user = userService.loadUserByUsername(loginUserDTO.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
            if(!authentication.isAuthenticated()) throw new InvalidParameterException("Password is incorrect.");
            JwtResponse jwtResponse = new JwtResponse( jwtService.generateToken(user.getUsername(), user.getUserId()));
            log.info("Login successful");
            return ResponseEntity.ok(jwtResponse.getToken());
        }
        catch (UsernameNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (InvalidParameterException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
