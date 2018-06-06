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
public class FileData {
    Long id;
    String name;
    String link;
    String fileHash;
    @JsonIgnore
    List<UserGroupData> groups;
}
