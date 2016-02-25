/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ataman
 */
@Entity
@Table(name = "personal_data", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalData.findAll", query = "SELECT p FROM PersonalData p"),
    @NamedQuery(name = "PersonalData.findById", query = "SELECT p FROM PersonalData p WHERE p.id = :id"),
    @NamedQuery(name = "PersonalData.findByFirstName", query = "SELECT p FROM PersonalData p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "PersonalData.findByLastName", query = "SELECT p FROM PersonalData p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "PersonalData.findByPatronymic", query = "SELECT p FROM PersonalData p WHERE p.patronymic = :patronymic"),
    @NamedQuery(name = "PersonalData.findByOrganization", query = "SELECT p FROM PersonalData p WHERE p.organization = :organization"),
    @NamedQuery(name = "PersonalData.findByCourse", query = "SELECT p FROM PersonalData p WHERE p.course = :course"),
    @NamedQuery(name = "PersonalData.findByCountry", query = "SELECT p FROM PersonalData p WHERE p.country = :country"),
    @NamedQuery(name = "PersonalData.findByCity", query = "SELECT p FROM PersonalData p WHERE p.city = :city")})
public class PersonalData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 30)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 30)
    @Column(name = "patronymic")
    private String patronymic;
    @Size(max = 100)
    @Column(name = "organization")
    private String organization;
    @Column(name = "course")
    private Short course;
    @Size(max = 50)
    @Column(name = "country")
    private String country;
    @Size(max = 50)
    @Column(name = "city")
    private String city;
    @OneToOne(mappedBy = "personalDataId")
    private Participation participation;

    public PersonalData() {
    }

    public PersonalData(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
        this.course = course;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalData)) {
            return false;
        }
        PersonalData other = (PersonalData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.PersonalData[ id=" + id + " ]";
    }
    
}
