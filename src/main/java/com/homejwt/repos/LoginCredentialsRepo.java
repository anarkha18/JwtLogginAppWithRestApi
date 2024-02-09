package com.homejwt.repos;


import com.homejwt.models.LoginCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginCredentialsRepo extends JpaRepository<LoginCredentials, Long> {
    LoginCredentials findByUserName(String userName);
}
