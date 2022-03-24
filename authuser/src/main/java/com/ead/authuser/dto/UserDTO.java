package com.ead.authuser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.apache.catalina.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImageUrlPut {}
    }

    private UUID userId;


    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    @Size(min = 4, max = 50)
    private String userName;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email
    @JsonView(UserView.RegistrationPost.class)
    @Size(min = 4, max = 50)
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    @Size(min = 6, max = 20)
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phone;

    @JsonView(UserView.RegistrationPost.class)
    private String cpf;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.ImageUrlPut.class})
    @JsonView({UserView.ImageUrlPut.class, UserView.RegistrationPost.class})
    private String imageUrl;

}
