package com.homejwt.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "jwtAuthTable")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginCredentials {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "user name cannot be blank")
    @Email(message = "invalid user name")
    @Column(unique = true)
    private String userName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Password must be at least 3 characters")
    private String password;

    @NotBlank(message = "Select a role")
    @Size(min = 3, message = "Role must be at least 3 characters")
    private String role;

}
