package com.pz.broadcast.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    Long id;
    String login;
    String password;
    String email;
    @JsonIgnore
    List<RoleData> roles;
    @JsonIgnore
    List<UserGroupData> groups;
    @JsonIgnore
    List<DeviceData> devices;
}


