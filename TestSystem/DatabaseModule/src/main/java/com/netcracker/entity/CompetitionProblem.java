/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ataman
 */
@Entity
@Table(name = "competition_problem", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetitionProblem.findAll", query = "SELECT c FROM CompetitionProblem c"),
    @NamedQuery(name = "CompetitionProblem.findById", query = "SELECT c FROM CompetitionProblem c WHERE c.id = :id"),
    @NamedQuery(name = "CompetitionProblem.findByProblemNumber", query = "SELECT c FROM CompetitionProblem c WHERE c.problemNumber = :problemNumber")})
public class CompetitionProblem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "problem_number")
    private String problemNumber;
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competition competitionId;
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Problem problemId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competitionProblemId")
    private List<ParticipationResult> participationResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competitionProblemId")
    private List<Submission> submissionList;

    public CompetitionProblem() {
    }

    public CompetitionProblem(Integer id) {
        this.id = id;
    }

    public CompetitionProblem(Integer id, String problemNumber) {
        this.id = id;
        this.problemNumber = problemNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(String problemNumber) {
        this.problemNumber = problemNumber;
    }

    public Competition getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Competition competitionId) {
        this.competitionId = competitionId;
    }

    public Problem getProblemId() {
        return problemId;
    }

    public void setProblemId(Problem problemId) {
        this.problemId = problemId;
    }

    @XmlTransient
    public List<ParticipationResult> getParticipationResultList() {
        return participationResultList;
    }

    public void setParticipationResultList(List<ParticipationResult> participationResultList) {
        this.participationResultList = participationResultList;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompetitionProblem)) {
            return false;
        }
        CompetitionProblem other = (CompetitionProblem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.CompetitionProblem[ id=" + id + " ]";
    }
    
}
