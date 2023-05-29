package com.example.bbm384oyd.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "department_managers")
public class DepartmentManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String photo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "department_manager_id")
    private List<FileDB> departmentManagerFiles;

    public DepartmentManager() {
    }

    public List<FileDB> getDepartmentManagerFiles() {
        return departmentManagerFiles;
    }
    public void setDepartmentManagerFiles(List<FileDB> departmentManagerSources) {
        this.departmentManagerFiles = departmentManagerSources;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "DepartmentManager [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password + ", photo=" + photo + "]";
    }
}
