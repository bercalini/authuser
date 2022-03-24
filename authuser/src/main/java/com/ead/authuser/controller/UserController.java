package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findOneUser(@PathVariable(value = "userId")UUID userId) {
        Optional<UserModel> user = userService.findById(userId);
        if(user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> user = userService.findById(userId);
        if(user.isPresent()) {
            userService.delete(user.get());
            return ResponseEntity.status(HttpStatus.OK).body("sucess user deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{userId}/updateUser")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @Validated(UserDTO.UserView.UserPut.class)
                                             @RequestBody
                                             @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        Optional<UserModel> userModel = userService.findById(userId);
        if (userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        BeanUtils.copyProperties(userDTO, userModel.get(),
                "userId", "userName", "email", "password", "cpf",
                    "imageUrl", "creationDate", "userStatus", "userType");
        userService.update(userModel.get());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/updatePassword")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @Validated(UserDTO.UserView.PasswordPut.class)
                                                 @JsonView(UserDTO.UserView.PasswordPut.class)
                                                 @RequestBody UserDTO userDTO) {
        Optional<UserModel> userModel = userService.findById(userId);
        if (userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!userModel.get().getPassword().equals(userDTO.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password not equals");
        }
        BeanUtils.copyProperties(userDTO, userModel.get(),
                "userId", "userName", "email", "cpf",
                "imageUrl", "creationDate", "userStatus", "userType", "fullName", "phone");
        userService.update(userModel.get());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/updateImage")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @Validated(UserDTO.UserView.ImageUrlPut.class)
                                              @JsonView(UserDTO.UserView.ImageUrlPut.class)
                                              @RequestBody UserDTO userDTO) {
        var userModel = userService.findById(userId);
        if (userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        BeanUtils.copyProperties(userDTO, userModel.get(),
                "userId", "userName", "email", "cpf", "password",
                "creationDate", "userStatus", "userType", "fullName", "phone");
        userService.update(userModel.get());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);

    }



}
