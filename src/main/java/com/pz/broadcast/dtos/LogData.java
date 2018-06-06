package com.pz.broadcast.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogData {
    Long id;
    String action;
    Date datetime;
    String comment;
    UserData user;
    FileData file;
}
