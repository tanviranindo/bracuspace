package com.project.bracuspace.service.user;

import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserDTOService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserDTOService(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public Page<UserDTO> findAllPageable(Pageable pageable) {
        Page<User> users = userService.findAllPageable(pageable);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageable, users.getTotalElements());
    }

    public Page<UserDTO> findByIdPageable(Long id, PageRequest pageRequest) {
        Page<User> users = userService.findByIdPageable(id, pageRequest);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageRequest, users.getTotalElements());
    }

    public UserDTO findByUsername(String username) {
        return modelMapper.map(userService.findByUsername(username), UserDTO.class);
    }

    public Page<UserDTO> findByNameContaining(String name, PageRequest pageRequest) {
        Page<User> users = userService.findByNameContaining(name, pageRequest);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageRequest, users.getTotalElements());
    }

    public Page<UserDTO> findBySurnameContaining(String Surname, PageRequest pageRequest) {
        Page<User> users = userService.findBySurnameContaining(Surname, pageRequest);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageRequest, users.getTotalElements());
    }

    public Page<UserDTO> findByUsernameContaining(String username, PageRequest pageRequest) {
        Page<User> users = userService.findByUsernameContaining(username, pageRequest);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageRequest, users.getTotalElements());
    }

    public Page<UserDTO> findByEmailContaining(String email, PageRequest pageRequest) {
        Page<User> users = userService.findByEmailContaining(email, pageRequest);
        List<UserDTO> UserDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
        return new PageImpl<>(UserDTOS, pageRequest, users.getTotalElements());
    }
}
