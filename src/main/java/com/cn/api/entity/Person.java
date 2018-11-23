package com.cn.api.entity;

import javax.persistence.*;

public class Person extends BaseEntity {
    @Id
    @Column(name = "PersonId")
    private Integer personid;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Sex")
    private String sex;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Position")
    private String position;

    @Column(name = "Degree")
    private Integer degree;

    /**
     * @return PersonId
     */
    public Integer getPersonid() {
        return personid;
    }

    /**
     * @param personid
     */
    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    /**
     * @return UserName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return Position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return Degree
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * @param degree
     */
    public void setDegree(Integer degree) {
        this.degree = degree;
    }
}