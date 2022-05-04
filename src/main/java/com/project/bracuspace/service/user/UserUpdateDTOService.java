package com.project.bracuspace.service.user;

import com.project.bracuspace.dto.UserUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateDTOService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserUpdateDTOService(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public UserUpdateDTO findById(Long id) {
        return modelMapper.map(userService.findByIdEagerly(id), UserUpdateDTO.class);
    }

    public UserUpdateDTO findByUsername(String name) {
        return modelMapper.map(userService.findByUsername(name), UserUpdateDTO.class);
    }


}
