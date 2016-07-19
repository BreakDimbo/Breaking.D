package com.limbo.hibernate.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by main on 7/2/16.
 */
@Entity
public class Teacher {
    private int id;
    private String name;
    private Set<Student> students = new HashSet<>();

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "s_t",
                joinColumns = {@JoinColumn(name = "teacher_id")},
                inverseJoinColumns = {@JoinColumn(name = "student_id")})
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
