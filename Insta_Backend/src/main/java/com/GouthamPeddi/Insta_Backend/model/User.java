package com.GouthamPeddi.Insta_Backend.model;

import com.GouthamPeddi.Insta_Backend.model.enums.AccountType;
import com.GouthamPeddi.Insta_Backend.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    @NotBlank(message = "password should not be blank")
    private String userPassword;

    private String userHandle;

    private String userBio;


    @Column(unique = true)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private boolean blueTick;


}
