package com.ead.authuser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID userId;
    private String userName;
    private String email;
    private String password;
    private String oldPassword;
    private String fullName;
    private String phone;
    private String cpf;
    private String imageUrl;


}
