package com.pz.broadcast.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleData {
    public RoleData(String name) {
        this.name = name;
    }

    Long id;
    String name;
    String description;
    @JsonIgnore
    List<UserData> users;
}


