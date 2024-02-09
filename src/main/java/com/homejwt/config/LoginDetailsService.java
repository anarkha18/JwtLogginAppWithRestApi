package com.homejwt.config;


import com.homejwt.models.LoginCredentials;
import com.homejwt.services.LoginCredentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginDetailsService  implements UserDetailsService {

    @Autowired
    private LoginCredentialService loginCredentialService;

    private static final Logger logger= LoggerFactory.getLogger(LoginDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginCredentials> user = Optional.ofNullable(loginCredentialService.getUserByUserName(username));
        return user.map(LoginUserDetails::new).orElseThrow(()-> {
            logger.error("User not found: {}", username);
            return new UsernameNotFoundException("User does not exists !!");
        });
    }
}
