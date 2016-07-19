package com.limbo.hibernate.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by main on 7/2/16.
 */

@Entity
public class Teacher {

    private int id;
    private String name;
    private String title;
    private String yourWifeName;
    private Date birthDate;
    private boolean good;
    private Gender gender;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    public String getYourWifeName() {
        return yourWifeName;
    }

    public void setYourWifeName(String yourWifeName) {
        this.yourWifeName = yourWifeName;
    }
}
