package com.example.bbm384oyd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "department_manager_sources")
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "file")
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
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String name) {
        this.fileName = name;
    }
    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }
    
}
