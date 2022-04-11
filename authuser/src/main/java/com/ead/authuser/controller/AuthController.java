package com.ead.authuser.controller;

import com.ead.authuser.assembler.UserAssembler;
import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @PostMapping("/signup")
    public ResponseEntity<Object> save(@RequestBody
                                           @Validated(UserDTO.UserView.RegistrationPost.class)
                                           @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        log.debug("Post register userDTO {}", userDTO.toString());
        if(userService.existsByUsername(userDTO.getUserName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User name is already taken");
        }
        if(userService.existsByEmail(userDTO.getEmail())){
            log.warn("Email {} is Already Taken ", userDTO.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
        }
        UserModel userModel = userAssembler.converterUserModelDTOToUserModel(userDTO);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        UserModel userModelSalvo = userService.save(userModel);
        log.debug("Post register userID {}", userModelSalvo.getUserId());
        log.info("Post saved successfully userID {}", userModelSalvo.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModelSalvo);
    }

    @GetMapping("/")
    public String logg() {
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return "teste";
    }

}
