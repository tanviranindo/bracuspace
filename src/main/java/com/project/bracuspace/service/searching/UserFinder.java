package com.project.bracuspace.service.searching;

import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.service.user.UserDTOService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.lang.Long.parseLong;

@Data
@Service
public class UserFinder {
    private UserDTOService UserDTOService;

    @Autowired
    public UserFinder(UserDTOService UserDTOService) {
        this.UserDTOService = UserDTOService;
    }

    public UserSearchResult searchUsersByProperty(PageRequest pageRequest, SearchParameters params) {
        Page<UserDTO> UserDTOPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        String s = params.getUsersProperty().get();

        switch (s) {
            case "ID":
                try {
                    UserDTOPage = UserDTOService.findByIdPageable(parseLong(params.getPropertyValue().get()), pageRequest);
                } catch (NumberFormatException e) {

                    return new UserSearchResult(UserDTOService.findAllPageable(pageRequest), true);
                }
                break;
            case "Name":
                UserDTOPage = UserDTOService.findByNameContaining(params.getPropertyValue().get(), pageRequest);
                break;
            case "Surname":
                UserDTOPage = UserDTOService.findBySurnameContaining(params.getPropertyValue().get(), pageRequest);
                break;
            case "Username":
                UserDTOPage = UserDTOService.findByUsernameContaining(params.getPropertyValue().get(), pageRequest);
                break;
            case "Email":
                UserDTOPage = UserDTOService.findByEmailContaining(params.getPropertyValue().get(), pageRequest);
                break;
        }
        return new UserSearchResult(UserDTOPage, false);
    }
}
