package com.pz.broadcast.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class UserGroup {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "groups")
    private List<User> users;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<File> files;


    protected UserGroup() {}

    public UserGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
