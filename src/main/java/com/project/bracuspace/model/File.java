package com.project.bracuspace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private Date lastModifiedDate;

    private Boolean directory;

    private Boolean file;

    private Long userId;


    public File() {
    }

    public File(String fileName) {
        this.fileName = fileName;
    }

    public File(String name, String mimeType, Long fileSize, Date lastModifiedDate, Boolean directory, Boolean file, Long userId) {
        this.fileName = name;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.lastModifiedDate = lastModifiedDate;
        this.directory = directory;
        this.file = file;
        this.userId = userId;
    }

    public File(String filename, Date modifiedDate, Long size) {
        this.fileName = filename;
        this.lastModifiedDate = modifiedDate;
        this.fileSize = size;
    }

    public File(String filename, Long size) {
        this.fileName = filename;
        this.fileSize = size;
    }
}