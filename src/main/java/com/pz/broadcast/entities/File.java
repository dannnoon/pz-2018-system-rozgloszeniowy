package com.pz.broadcast.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class File {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String link;
    private String fileHash;
    @ManyToMany(mappedBy = "files")
    private List<UserGroup> groups;


    protected File() {}

    public File(String name, String link, String fileHash) {
        this.name = name;
        this.link = link;
        this.fileHash = fileHash;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", fileHash='" + fileHash + '\'' +
                '}';
    }
}
