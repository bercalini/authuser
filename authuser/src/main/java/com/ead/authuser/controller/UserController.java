package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.UserSpecification;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> findAllUsers(UserSpecification.UserSpec userSpec,
                                                        @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                        @RequestParam(required = false) UUID courseId) {
        Page<UserModel> listPageUserModel = null;
        if(courseId != null) {
            listPageUserModel = userService.findAll(UserSpecification.userCourseId(courseId).and(userSpec), pageable);
        } else {
            listPageUserModel =userService.findAll(userSpec, pageable);
        }
        if(!listPageUserModel.isEmpty()) {
            listPageUserModel.toList().stream().forEach(
                   user -> {
                       user.add(linkTo(methodOn(UserController.class).findOneUser(user.getUserId())).withSelfRel());
                   });
        }
        return ResponseEntity.status(HttpStatus.OK).body(listPageUserModel);
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
        log.debug("DELETE delete by id {} ", userId);
        Optional<UserModel> user = userService.findById(userId);
        if (user.isPresent()) {
            userService.delete(user.get());
            log.info("DELETA com sucesso {}", userId);
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
        log.debug("UPDATE updateUser userDTO {} ", userDTO);
        Optional<UserModel> userModel = userService.findById(userId);
        if (userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        BeanUtils.copyProperties(userDTO, userModel.get(),
                "userId", "userName", "email", "password", "cpf",
                    "imageUrl", "creationDate", "userStatus", "userType");
        UserModel userModeAlterado = userService.update(userModel.get());
        log.info("UPDATE com sucesso ID {} ", userModeAlterado.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userModeAlterado);
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
