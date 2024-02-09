package com.homejwt.services;

import com.homejwt.models.LoginCredentials;
import com.homejwt.repos.LoginCredentialsRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoginCredentialService {

    private static final Logger logger = LoggerFactory.getLogger(LoginCredentialService.class);

    private LoginCredentialsRepo loginCredentialsRepo;
    private newUserService newUserService;
   public void addUsers(){
       loginCredentialsRepo.saveAll(newUserService.myUsers());
       logger.info("saved");
   }

   public LoginCredentials getUserByUserName(String userName){
       logger.info(userName);
       return loginCredentialsRepo.findByUserName(userName);
   }

}
