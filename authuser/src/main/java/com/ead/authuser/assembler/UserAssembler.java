package com.ead.authuser.assembler;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserModel converterUserModelDTOToUserModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserModel.class);
    }

}
