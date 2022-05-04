package com.project.bracuspace.controller;

import com.project.bracuspace.model.User;
import com.project.bracuspace.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RestControllerUser {
    private final UserService userService;

    public RestControllerUser(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/json-users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = userService.findAll();

        if (allUsers == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        else if (allUsers.isEmpty()) return new ResponseEntity<>(allUsers, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/admin/json-users/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userToDelete = userService.findById(id);

        if (userToDelete.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.deleteById(id);
        return new ResponseEntity<>(userToDelete.get(), HttpStatus.NO_CONTENT);
    }


}