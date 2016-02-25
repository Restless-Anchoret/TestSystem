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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ataman
 */
@Entity
@Table(name = "author_decision", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthorDecision.findAll", query = "SELECT a FROM AuthorDecision a"),
    @NamedQuery(name = "AuthorDecision.findById", query = "SELECT a FROM AuthorDecision a WHERE a.id = :id"),
    @NamedQuery(name = "AuthorDecision.findByFolderName", query = "SELECT a FROM AuthorDecision a WHERE a.folderName = :folderName")})
public class AuthorDecision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "folder_name")
    private String folderName;
    @JoinColumn(name = "compilator_id", referencedColumnName = "id")
    @ManyToOne
    private Compilator compilatorId;
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Problem problemId;

    public AuthorDecision() {
    }

    public AuthorDecision(Integer id) {
        this.id = id;
    }

    public AuthorDecision(Integer id, String folderName) {
        this.id = id;
        this.folderName = folderName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Compilator getCompilatorId() {
        return compilatorId;
    }

    public void setCompilatorId(Compilator compilatorId) {
        this.compilatorId = compilatorId;
    }

    public Problem getProblemId() {
        return problemId;
    }

    public void setProblemId(Problem problemId) {
        this.problemId = problemId;
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
        if (!(object instanceof AuthorDecision)) {
            return false;
        }
        AuthorDecision other = (AuthorDecision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.AuthorDecision[ id=" + id + " ]";
    }
    
}
