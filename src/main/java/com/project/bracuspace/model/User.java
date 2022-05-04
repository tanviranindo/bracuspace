package com.project.bracuspace.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, String username, String email, String password, boolean enabled) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String name, String surname, String username, String email, String password, boolean enabled, List<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
}
