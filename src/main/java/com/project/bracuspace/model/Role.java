package com.project.bracuspace.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.bracuspace.annotation.ValidRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ValidRole
    @Column(unique = true)
    private String name;

    @JsonManagedReference
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

}