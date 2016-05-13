package com.netcracker.database.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "author_decision", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthorDecision.findById", query = "SELECT a FROM AuthorDecision a WHERE a.id = :id")})
public class AuthorDecision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(min = 1, max = 15)
    @Column(name = "folder_name")
    private String folderName;
    @JoinColumn(name = "compilator_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Compilator compilatorId;

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

    /*public Problem getProblemId() {
        return problemId;
    }

    public void setProblemId(Problem problemId) {
        this.problemId = problemId;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
