package com.example.bbm384oyd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "department_manager_sources")
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String file_name;
    
    
    private byte[] file;

/*     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private DepartmentManager departmentManager; */


    public FileDB() {
    }

   /*  public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    } */
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

    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
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
