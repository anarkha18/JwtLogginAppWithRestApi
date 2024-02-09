package com.homejwt.controllers;

import com.homejwt.config.JwtHelper;
import com.homejwt.config.LoginDetailsService;
import com.homejwt.models.AuthRequest;
import com.homejwt.models.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginDetailsService loginDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper jwtHelper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateAnsGetToken(@RequestBody AuthRequest request) {
        UserDetails userDetails = loginDetailsService.loadUserByUsername(request.getUserName());
        Authentication authentication =manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtHelper.generateToken(userDetails);
            AuthResponse response = AuthResponse.builder().jwtToken(token).userName(request.getUserName()).build();
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            throw new UsernameNotFoundException("invalid user request!!!");
        }

    }


}
