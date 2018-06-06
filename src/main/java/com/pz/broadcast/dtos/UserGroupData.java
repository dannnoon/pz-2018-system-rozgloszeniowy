package com.pz.broadcast.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupData {
    Long id;
    String name;
    String description;
    List<UserData> users;
    List<FileData> files;
}
