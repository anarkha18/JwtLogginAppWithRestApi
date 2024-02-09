package com.homejwt.services;

import com.homejwt.models.LoginCredentials;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class newUserService {
    private PasswordEncoder passwordEncoder;

    public List<LoginCredentials> myUsers(){
        ArrayList<LoginCredentials> users = new ArrayList<>();
        return users;
    }
}
