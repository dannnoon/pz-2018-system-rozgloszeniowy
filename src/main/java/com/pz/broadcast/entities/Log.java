package com.pz.broadcast.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Log {

    @Id
    @GeneratedValue
    private Long id;
    private String action;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    private String comment;
    @ManyToOne
    private User user;
    @ManyToOne
    private File file;


    protected Log() {}

    public Log(String action, Date datetime, String comment) {
        this.action = action;
        this.datetime = datetime;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", datetime=" + datetime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
