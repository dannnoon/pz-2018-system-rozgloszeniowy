package com.pz.broadcast.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Device {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String pushToken;
    private String IPAddress;
    @ManyToOne
    private User user;


    protected Device() {
    }

    public Device(String name, String pushToken, String IPAddress) {
        this.name = name;
        this.pushToken = pushToken;
        this.IPAddress = IPAddress;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pushToken='" + pushToken + '\'' +
                ", IPAddress='" + IPAddress + '\'' +
                '}';
    }
}
