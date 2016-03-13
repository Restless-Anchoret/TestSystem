/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ataman
 */
@Entity
@Table(name = "participation", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participation.findById", query = "SELECT p FROM Participation p WHERE p.id = :id")})
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registered")
    private boolean registered;
    @Column(name = "points")
    private Short points;
    @Column(name = "fine")
    private Integer fine;
    @Column(name = "place")
    private Short place;
    @Column(name = "solved_problems")
    private Short solvedProblems;
    /*@JoinColumn(name = "competition_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competition competitionId;*/
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PersonalData personalDataId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User userId;

    public Participation() {
    }

    public Participation(Integer id) {
        this.id = id;
    }

    public Participation(Integer id, boolean registered) {
        this.id = id;
        this.registered = registered;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public Short getPoints() {
        return points;
    }

    public void setPoints(Short points) {
        this.points = points;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public Short getPlace() {
        return place;
    }

    public void setPlace(Short place) {
        this.place = place;
    }

    public Short getSolvedProblems() {
        return solvedProblems;
    }

    public void setSolvedProblems(Short solvedProblems) {
        this.solvedProblems = solvedProblems;
    }

    /*public Competition getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Competition competitionId) {
        this.competitionId = competitionId;
    }*/

    public PersonalData getPersonalDataId() {
        return personalDataId;
    }

    public void setPersonalDataId(PersonalData personalDataId) {
        this.personalDataId = personalDataId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Participation)) {
            return false;
        }
        Participation other = (Participation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.Participation[ id=" + id + " ]";
    }
    
}
