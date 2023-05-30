package com.example.bbm384oyd.model;

import org.checkerframework.checker.units.qual.C;
import org.springframework.core.codec.ByteArrayDecoder;

import jakarta.persistence.*;

@Entity
@Table(name = "department_manager_sources")
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="file_name")
    private String file_name;
    

    @Column(name = "file", length = 2000000000)
    private String file;
    
    public FileDB() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFile_name() {
        return file_name;
    }
    public void setFile_name(String name) {
        this.file_name = name;
    }

    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileDB{" +
                "id=" + id +
                ", name='" + file_name + '\'' +
                ", file=" + file +
                '}';
    }
    
}
