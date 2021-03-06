package com.netcracker.database.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "compilator", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compilator.findAll", query = "SELECT c FROM Compilator c ORDER BY c.id"),
    @NamedQuery(name = "Compilator.findById", query = "SELECT c FROM Compilator c WHERE c.id = :id"),
    @NamedQuery(name = "Compilator.findByName", query = "SELECT c FROM Compilator c WHERE c.name = :name")})
public class Compilator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "compilatorId", fetch = FetchType.LAZY)
    private List<AuthorDecision> authorDecisionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compilatorId", fetch = FetchType.LAZY)
    private List<Submission> submissionList;

    public Compilator() {
    }

    public Compilator(Integer id) {
        this.id = id;
    }

    public Compilator(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<AuthorDecision> getAuthorDecisionList() {
        return authorDecisionList;
    }

    public void setAuthorDecisionList(List<AuthorDecision> authorDecisionList) {
        this.authorDecisionList = authorDecisionList;
    }

    @XmlTransient
    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compilator)) {
            return false;
        }
        Compilator other = (Compilator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.Compilator[ id=" + id + " ]";
    }
    
}
